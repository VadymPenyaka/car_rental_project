package nulp.cs.carrentalrestservice.modules.payment.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.model.TODO;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.service.OrderService;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentStatus;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeRequest;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeResponse;
import nulp.cs.carrentalrestservice.modules.payment.enity.Payment;
import nulp.cs.carrentalrestservice.modules.payment.repository.PaymentRepository;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.beans.factory.annotation.Value;
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

    private final PaymentRepository paymentRepository;
    private final LoggingService log;
    private final OrderService orderService;

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
    public boolean processSuccessfulPayment(String paymentIntentId) {
        Payment payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(()->new NotFoundException("Payment not found!"));

//        TODO create special exception
        if (payment.getStatus() == PaymentStatus.PAID) {
            log.logInfo("Payment already completed");
            return true;
        }

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());

            updatePaymentWithChargeDetails(payment, paymentIntent);

            paymentRepository.save(payment);

//            confirmOrder(payment);

            log.logInfo("Payment successfully processed: " + paymentIntentId);
            return true;

        } catch (StripeException e) {
            log.logInfo("Error retrieving PaymentIntent from Stripe: " + paymentIntentId);
            return false;
        } catch (Exception e) {
            log.logInfo("Error processing successful payment: " + paymentIntentId);
            return false;
        }
    }

    @Transactional
    public boolean processFailedPayment(String paymentIntentId) {
        Payment payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(()->new NotFoundException("Payment not found!"));

        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            payment.setStatus(PaymentStatus.FAILED);

            if (paymentIntent.getLastPaymentError() != null) {
                String errorMessage = paymentIntent.getLastPaymentError().getMessage();
                log.logInfo("Payment failed with error: " + paymentIntentId);
            }

            paymentRepository.save(payment);

            log.logInfo("Payment marked as failed: " + paymentIntentId);
            return true;

        } catch (StripeException e) {
            log.logInfo("Error retrieving PaymentIntent from Stripe: " + paymentIntentId);
            return false;
        } catch (Exception e) {
            log.logInfo("Error processing successful payment: " + paymentIntentId);
            return false;
        }
    }

    @Transactional
    public boolean processCompletedSession(String sessionId) {
        Payment payment = paymentRepository.findBySessionId(sessionId)
                .orElseThrow(()-> new NotFoundException("Payment not found!"));

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
//                confirmOrder(payment);
            }

            log.logInfo("Session successfully processed: " + sessionId);
            return true;

        } catch (StripeException e) {
            log.logInfo("Error retrieving session from Stripe: " + sessionId);
            return false;
        } catch (Exception e) {
            log.logInfo("Error processing completed session: " + sessionId);
            return false;
        }
    }


    @Transactional
    public boolean processCancelledPayment(String paymentIntentId) {
        Payment payment = paymentRepository.findByPaymentIntentId(paymentIntentId)
                .orElseThrow(()->new NotFoundException("Payment not found!"));

        payment.setStatus(PaymentStatus.PAID);
        paymentRepository.save(payment);

        log.logInfo("Payment cancelled: " + paymentIntentId);
        return true;
    }
    //TODO use
    public Optional<Payment> findByPaymentIntentId(String paymentIntentId) {
        return paymentRepository.findByPaymentIntentId(paymentIntentId);
    }

    public Optional<Payment> findBySessionId(String sessionId) {
        return paymentRepository.findBySessionId(sessionId);
    }
    //TODO make it work
    private void updatePaymentWithChargeDetails(Payment payment, PaymentIntent paymentIntent) {
//        if (!paymentIntent.getCharges().getData().isEmpty()) {
//            Charge charge = paymentIntent.getCharges().getData().get(0);
//
//            payment.setReceiptUrl(charge.getReceiptUrl());
//
//            if (charge.getPaymentMethodDetails() != null &&
//                    charge.getPaymentMethodDetails().getCard() != null) {
//                var card = charge.getPaymentMethodDetails().getCard();
//                payment.setCardBrand(card.getBrand());
//                payment.setCardLastDigits(card.getLast4());
//            }
//        }
    }

//    private void confirmOrder(Payment payment) {
//        try {
//            orderService.confirmOrderPayment(payment.getOrder().getId(), payment.getPaymentIntentId());
//            log.info("Order confirmed: {}", payment.getOrder().getId());
//        } catch (Exception e) {
//            log.error("Failed to confirm order: {}", payment.getOrder().getId(), e);
//            // Не кидаємо exception - платіж все одно успішний
//        }
//    }

    private void createPaymentRecord(StripeRequest request, Session session) {
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentIntentId(session.getPaymentIntent());
        payment.setSessionId(session.getId());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrder(request.getCarOrder());

        paymentRepository.save(payment);
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
                .setSuccessUrl(domain + "/api/v1/success")
                .setCancelUrl(domain + "/api/v1/cancel")
                .addLineItem(lineItem)
                .build();
    }

}
