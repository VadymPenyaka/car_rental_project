package nulp.cs.carrentalrestservice.modules.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.ChargeListParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import nulp.cs.carrentalrestservice.modules.order.service.OrderService;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentStatus;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeRequest;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeResponse;
import nulp.cs.carrentalrestservice.modules.payment.enity.Payment;
import nulp.cs.carrentalrestservice.modules.payment.repository.PaymentRepository;
import nulp.cs.carrentalrestservice.shared.event.OrderEmailEvent;
import nulp.cs.carrentalrestservice.shared.event.OrderStatusEvent;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${api.domain}")
    private String domain;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private final PaymentRepository paymentRepository;
    private final LoggingService log;
    private final ApplicationEventPublisher publisher;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public StripeResponse createPaymentLink(StripeRequest request) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams sessionParams = createSessionParams(request);

        Session session;
        try {
            session = Session.create(sessionParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        createPaymentRecord(request, session);

        return StripeResponse.builder()
                .sessionId(session.getId())
                .status("SUCCESS")
                .url(session.getUrl())
                .build();
    }

    @Transactional
    public boolean processWebhookEvent(String payload, String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            log.logInfo("Processing webhook event: " + event.getType());

            return switch (event.getType()) {
                case "payment_intent.succeeded" -> handlePaymentSuccess(event);
                case "payment_intent.payment_failed", "payment_intent.canceled" -> handlePaymentFailure(event);
                case "checkout.session.completed" -> handleSessionCompleted(event);
                case "checkout.session.expired" -> handleSessionExpired(event);
                default -> {
                    log.logDebug("Unhandled event type: " + event.getType());
                    yield true;
                }
            };

        } catch (SignatureVerificationException e) {
            log.logError("Invalid webhook signature", e);
            return false;
        } catch (Exception e) {
            log.logError("Error processing webhook", e);
            return false;
        }
    }


    private boolean handlePaymentSuccess(Event event) {
        try {
            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                    .getObject().orElse(null);

            if (paymentIntent == null) {
//                log.logError("Failed to deserialize PaymentIntent");
                return false;
            }

            return processSuccessfulPayment(paymentIntent.getId());
        } catch (Exception e) {
            log.logError("Error handling payment success", e);
            return false;
        }
    }

    private boolean handlePaymentFailure(Event event) {
        try {
            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                    .getObject().orElse(null);

            if (paymentIntent == null) {
//                log.logError("Failed to deserialize PaymentIntent for failure");
                return false;
            }

            return processFailedPayment(paymentIntent.getId());

        } catch (Exception e) {
            log.logError("Error handling payment failure", e);
            return false;
        }
    }

    private boolean handleSessionCompleted(Event event) {
        try {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject().orElse(null);

            if (session == null) {
//                log.logError("Failed to deserialize Session");
                return false;
            }

            return processCompletedSession(session.getId());

        } catch (Exception e) {
            log.logError("Error handling session completion", e);
            return false;
        }
    }

    private boolean handleSessionExpired(Event event) {
        try {
            Session session = (Session) event.getDataObjectDeserializer()
                    .getObject().orElse(null);

            if (session == null) {
//                log.logError("Failed to deserialize expired Session");
                return true; // Не критично
            }

            log.logInfo("Processing expired session: " + session.getId());
            // Тут можна додати логіку для обробки прострочених сесій
            return true;

        } catch (Exception e) {
            log.logError("Error handling session expiration", e);
            return false;
        }
    }

    @Transactional
    public boolean processSuccessfulPayment(String paymentIntentId) {
        Optional<Payment> paymentOpt = paymentRepository.findByPaymentIntentId(paymentIntentId);

        if (paymentOpt.isEmpty()) {
            log.logError("Payment not found for PaymentIntent: " + paymentIntentId, new NotFoundException());
            return false;
        }

        Payment payment = paymentOpt.get();

        if (payment.getStatus() == PaymentStatus.PAID) {
            log.logInfo("Payment already completed: " + paymentIntentId);
            return true;
        }

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());

            updatePaymentWithChargeDetails(payment, paymentIntent);

            paymentRepository.save(payment);

            confirmOrder(payment);

            log.logInfo("Payment successfully processed: " + paymentIntentId);
            return true;

        } catch (StripeException e) {
            log.logError("Error retrieving PaymentIntent from Stripe: " + paymentIntentId, e);
            return false;
        } catch (Exception e) {
            log.logError("Error processing successful payment: " + paymentIntentId, e);
            return false;
        }
    }

    @Transactional
    public boolean processFailedPayment(String paymentIntentId) {
        Optional<Payment> paymentOpt = paymentRepository.findByPaymentIntentId(paymentIntentId);

        if (paymentOpt.isEmpty()) {
            log.logError("Payment not found for PaymentIntent: " + paymentIntentId, new NotFoundException());
            return false;
        }

        Payment payment = paymentOpt.get();

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            payment.setStatus(PaymentStatus.FAILED);

            if (paymentIntent.getLastPaymentError() != null) {
                String errorMessage = paymentIntent.getLastPaymentError().getMessage();
                log.logInfo("Payment failed with error: " + errorMessage);
            }

            paymentRepository.save(payment);

            log.logInfo("Payment marked as failed: " + paymentIntentId);
            return true;

        } catch (StripeException e) {
            log.logError("Error retrieving failed PaymentIntent from Stripe: " + paymentIntentId, e);
            return false;
        } catch (Exception e) {
            log.logError("Error processing failed payment: " + paymentIntentId, e);
            return false;
        }
    }

    @Transactional
    public boolean processCompletedSession(String sessionId) {
        Optional<Payment> paymentOpt = paymentRepository.findBySessionId(sessionId);

        if (paymentOpt.isEmpty()) {
            log.logError("Payment not found for session id: " + sessionId, new NotFoundException());
            return false;
        }

        Payment payment = paymentOpt.get();

        try {
            Session session = Session.retrieve(sessionId);

            if (!"paid".equals(session.getPaymentStatus())) {
                log.logInfo("Session not paid: " + sessionId);
                return true;
            }

            if (payment.getStatus() != PaymentStatus.PAID) {
                payment.setStatus(PaymentStatus.PAID);
                payment.setPaidAt(LocalDateTime.now());

                if (session.getCustomerDetails() != null && session.getCustomerDetails().getEmail() != null) {
                    log.logInfo("Customer email from session: " + session.getCustomerDetails().getEmail());
                }

                paymentRepository.save(payment);
                confirmOrder(payment);
            }

            log.logInfo("Session successfully processed: " + sessionId);
            return true;

        } catch (StripeException e) {
            log.logError("Error retrieving session from Stripe: " + sessionId, e);
            return false;
        } catch (Exception e) {
            log.logError("Error processing completed session: " + sessionId, e);
            return false;
        }
    }

    @Transactional
    public boolean processCancelledPayment(String paymentIntentId) {
        Optional<Payment> paymentOpt = paymentRepository.findByPaymentIntentId(paymentIntentId);

        if (paymentOpt.isEmpty()) {
            log.logError("Payment not found for PaymentIntent: " + paymentIntentId, new NotFoundException());
            return false;
        }

        Payment payment = paymentOpt.get();
        payment.setStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);

        log.logInfo("Payment cancelled: " + paymentIntentId);
        return true;
    }

    public Optional<Payment> findByPaymentIntentId(String paymentIntentId) {
        return paymentRepository.findByPaymentIntentId(paymentIntentId);
    }

    public Optional<Payment> findBySessionId(String sessionId) {
        return paymentRepository.findBySessionId(sessionId);
    }

    private void updatePaymentWithChargeDetails(Payment payment, PaymentIntent paymentIntent) {
        try {
            // Спочатку спробуємо через latest_charge
            String latestChargeId = paymentIntent.getLatestCharge();

            if (latestChargeId != null && !latestChargeId.isEmpty()) {
                Charge charge = Charge.retrieve(latestChargeId);
                updatePaymentFromCharge(payment, charge);
                return;
            }

            // Якщо latest_charge недоступний, використовуємо Charge.list()
            ChargeCollection charges = Charge.list(
                    ChargeListParams.builder()
                            .setPaymentIntent(paymentIntent.getId())
                            .setLimit(1L)
                            .build()
            );

            if (charges.getData() != null && !charges.getData().isEmpty()) {
                Charge charge = charges.getData().get(0);
                updatePaymentFromCharge(payment, charge);
            } else {
                log.logDebug("No charges found for PaymentIntent: " + paymentIntent.getId());
            }

        } catch (StripeException e) {
            log.logError("Error retrieving charges for PaymentIntent: " + paymentIntent.getId(), e);
        }
    }

    private void updatePaymentFromCharge(Payment payment, Charge charge) {
        try {
            // Оновлюємо receipt URL
            if (charge.getReceiptUrl() != null && !charge.getReceiptUrl().isEmpty()) {
                payment.setReceiptUrl(charge.getReceiptUrl());
                log.logDebug("Updated receipt URL for payment: " + payment.getId());
            }

            // Оновлюємо інформацію про картку
            if (charge.getPaymentMethodDetails() != null) {
                var paymentMethodDetails = charge.getPaymentMethodDetails();

                if (paymentMethodDetails.getCard() != null) {
                    var card = paymentMethodDetails.getCard();

                    if (card.getBrand() != null) {
                        payment.setCardBrand(card.getBrand());
                    }

                    if (card.getLast4() != null) {
                        payment.setCardLastDigits(card.getLast4());
                    }

                    log.logDebug("Updated card details for payment: " + payment.getId() +
                            " - " + card.getBrand() + " ending in " + card.getLast4());
                }
            }

        } catch (Exception e) {
            log.logError("Error updating payment from charge: " + charge.getId(), e);
        }
    }

    private void confirmOrder(Payment payment) {
        try {
            publisher.publishEvent(new OrderStatusEvent(this, payment.getOrder().getId(), OrderStatus.PAID));
            log.logInfo("Order confirmed: " + payment.getOrder().getId());
        } catch (Exception e) {
            log.logError("Failed to confirm order: " + payment.getOrder().getId(), e);
        }
    }

    private void createPaymentRecord(StripeRequest request, Session session) {
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentIntentId(session.getPaymentIntent());
        payment.setSessionId(session.getId());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrder(request.getCarOrder());
        System.out.println(payment);
        paymentRepository.save(payment);
        log.logInfo("Payment record created for session: " + payment.getSessionId());
    }

    private SessionCreateParams createSessionParams(StripeRequest request) {
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams
                .LineItem.PriceData.ProductData.builder()
                .setName(request.getName())
                .build();

        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams
                .LineItem.PriceData.builder()
                .setCurrency(request.getCurrency())
                .setProductData(productData)
                .setUnitAmountDecimal(request.getAmount().multiply(BigDecimal.valueOf(100)))
                .build();

        SessionCreateParams.LineItem lineItem = SessionCreateParams
                .LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(1L)
                .build();

        return SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domain + "/api/v1/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(domain + "/api/v1/cancel?session_id={CHECKOUT_SESSION_ID}")
                .addLineItem(lineItem)
//                .putMetadata("order_id", request.getCarOrder().getId().toString())
                .build();
    }
}