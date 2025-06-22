package nulp.cs.carrentalrestservice.modules.payment.service;

import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentChargeData;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentStatus;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeRequest;
import nulp.cs.carrentalrestservice.modules.payment.enity.Payment;
import nulp.cs.carrentalrestservice.modules.payment.repository.PaymentRepository;
import nulp.cs.carrentalrestservice.shared.event.OrderStatusEvent;
import nulp.cs.carrentalrestservice.shared.event.PaymentStatusEvent;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
//TODO make all method parse parameter using json
//TODO refactor handle methods extract common parts(exception handling, get payment from db)
//TODO extract handle methods to separate stripe class
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final LoggingService log;
    private final ApplicationEventPublisher publisher;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean processCompletedSession(String sessionId, String paymentIntentId) {
        try {
            Optional<Payment> paymentOpt = paymentRepository.findBySessionId(sessionId);

            if (paymentOpt.isEmpty()) {
//                log.logError("Payment not found for session: " + sessionId);
                return false;
            }

            Payment payment = paymentOpt.get();

            payment = paymentRepository.findByIdWithLock(payment.getId())
                    .orElseThrow(() -> new NotFoundException("Payment not found: "));

            Session session = Session.retrieve(sessionId);

            boolean needsSave = false;
            if (payment.getPaymentIntentId() == null && session.getPaymentIntent() != null) {
                payment.setPaymentIntentId(session.getPaymentIntent());
                needsSave = true;
                log.logInfo("Set PaymentIntentId: " + sessionId + " -> " + session.getPaymentIntent());
            }

            if ("paid".equals(session.getPaymentStatus()) && payment.getStatus() != PaymentStatus.PAID) {
                payment.setStatus(PaymentStatus.PAID);
                payment.setPaidAt(LocalDateTime.now());
                needsSave = true;
                log.logInfo("Updated status to PAID: " + sessionId);
            }

            if (needsSave) {
                paymentRepository.saveAndFlush(payment);
            }

            return true;

        } catch (StripeException e) {
            log.logError("Stripe API error for session: " + sessionId, e);
            throw new RuntimeException("Stripe API error", e);
        } catch (Exception e) {
            log.logError("Error processing session: " + sessionId, e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean processSuccessfulPayment(String paymentIntentId, PaymentChargeData chargeData) {
        try {
            Payment payment = findPaymentWithRetry(paymentIntentId, 3);

            if (payment == null) {
                return false;
            }

            payment = paymentRepository.findByIdWithLock(payment.getId())
                    .orElseThrow(() -> new NotFoundException("Payment not found: "));

            if (payment.getStatus() == PaymentStatus.PAID) {
                log.logInfo("Payment already completed: " + paymentIntentId);
                return true;
            }

            payment.setStatus(PaymentStatus.PAID);
            payment.setPaidAt(LocalDateTime.now());

            updatePaymentWithChargeData(payment, chargeData);

            paymentRepository.saveAndFlush(payment);

            confirmOrder(payment);

            log.logInfo("Payment successfully processed: " + paymentIntentId);
            eventPublisher.publishEvent(new PaymentStatusEvent(this, PaymentStatus.PAID, payment.getCustomerEmail()));
            return true;
        } catch (Exception e) {
            log.logError("Error processing successful payment: " + paymentIntentId, e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean processFailedPayment(String paymentIntentId, String errorMessage) {
        try {
            Payment payment = findPaymentWithRetry(paymentIntentId, 3);

            if (payment == null) {
                return false;
            }

            payment = paymentRepository.findByIdWithLock(payment.getId())
                    .orElseThrow(() -> new NotFoundException("Payment not found: "));

            payment.setStatus(PaymentStatus.FAILED);

            if (errorMessage != null) {
                log.logInfo("Payment failed with error: " + errorMessage);
            }

            paymentRepository.saveAndFlush(payment);

            return true;

        } catch (Exception e) {
            log.logError("Error processing failed payment: " + paymentIntentId, e);
            throw e;
        }
    }

    private Payment findPaymentWithRetry(String paymentIntentId, int maxRetries) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                Optional<Payment> paymentOpt = paymentRepository.findByPaymentIntentId(paymentIntentId);

                if (paymentOpt.isPresent()) {
                    return paymentOpt.get();
                }

                if (attempt < maxRetries) {
                    Thread.sleep(100 * attempt);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.logError("Error during attempt " + attempt, e);
            }
        }

        return null;
    }

//    public Payment findByPaymentIntentId(String paymentIntentId) {
//        return paymentRepository.findByPaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> new NotFoundException("Payment not found for PaymentIntent: " + paymentIntentId));
//    }
//
//    public Payment findBySessionId(String sessionId) {
//        return paymentRepository.findBySessionId(sessionId)
//                .orElseThrow(() -> new NotFoundException("Payment not found for session: " + sessionId));
//    }

    private void confirmOrder(Payment payment) {
        try {
            publisher.publishEvent(new OrderStatusEvent(this, payment.getOrder().getId(), OrderStatus.PAID));
            log.logInfo("Order confirmed: " + payment.getOrder().getId());
        } catch (Exception e) {
            log.logError("Failed to confirm order: " + payment.getOrder().getId(), e);
        }
    }

    public void createPaymentRecord(StripeRequest request, Session session) {
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setSessionId(session.getId());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setOrder(request.getCarOrder());
        payment.setCustomerEmail(request.getCustomerEmail());
        Payment savedPayment = paymentRepository.save(payment);
        log.logInfo("Payment record created for session: " + savedPayment.getSessionId());
    }
    private void updatePaymentWithChargeData(Payment payment, PaymentChargeData chargeData) {
        if (chargeData.getReceiptUrl() != null && payment.getReceiptUrl() == null) {
            payment.setReceiptUrl(chargeData.getReceiptUrl());
        }

        if (chargeData.getCardBrand() != null && payment.getCardBrand() == null) {
            payment.setCardBrand(chargeData.getCardBrand());
        }

        if (chargeData.getCardLastDigits() != null && payment.getCardLastDigits() == null) {
            payment.setCardLastDigits(chargeData.getCardLastDigits());
        }
    }
}