package nulp.cs.carrentalrestservice.modules.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.ChargeListParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentChargeData;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeRequest;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeResponse;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StripeService {
    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    @Value("${api.domain}")
    private String domain;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    private final LoggingService log;
    private final PaymentService paymentService;

    public StripeResponse createPaymentLink(StripeRequest request) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams sessionParams = createSessionParams(request);

        Session session;
        try {
            session = Session.create(sessionParams);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }

        paymentService.createPaymentRecord(request, session);

        return StripeResponse.builder()
                .sessionId(session.getId())
                .status("SUCCESS")
                .url(session.getUrl())
                .build();
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
                .putMetadata("orderId", request.getCarOrder().getId().toString())
                .build();
    }

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

    public boolean handlePaymentSuccess(Event event) {
        try {
            JsonNode paymentIntentNode = extractEventDataObject(event);
            String paymentIntentId = getRequiredStringField(paymentIntentNode, "id", "Payment ID");

            PaymentChargeData chargeData = getChargeData(paymentIntentId);

            return paymentService.processSuccessfulPayment(paymentIntentId, chargeData);
        } catch (Exception e) {
            log.logError("Error handling payment success", e);
            return false;
        }
    }

    private boolean handlePaymentFailure(Event event) {
        try {
            JsonNode paymentIntentNode = extractEventDataObject(event);
            String paymentIntentId = getRequiredStringField(paymentIntentNode, "id", "Payment ID");

            String errorMessage = getPaymentErrorMessage(paymentIntentId);

            return paymentService.processFailedPayment(paymentIntentId, errorMessage);
        } catch (Exception e) {
            log.logError("Error handling payment failure", e);
            return false;
        }
    }

    public boolean handleSessionCompleted(Event event) {
        try {
            JsonNode sessionNode = extractEventDataObject(event);

            String sessionId = getRequiredStringField(sessionNode, "id", "Session ID");
            String paymentIntentId = getOptionalStringField(sessionNode, "payment_intent");
            String paymentStatus = getOptionalStringField(sessionNode, "payment_status");

            String customerEmail = null;
            JsonNode customerDetailsNode = sessionNode.get("customer_details");
            if (customerDetailsNode != null) {
                customerEmail = getOptionalStringField(customerDetailsNode, "email");
            }

            log.logInfo("Processing session completed: " + sessionId +
                    ", PaymentIntent: " + paymentIntentId +
                    ", Status: " + paymentStatus +
                    ", Customer: " + customerEmail);

            return paymentService.processCompletedSession(sessionId, paymentIntentId);

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
                return true;
            }

            log.logInfo("Processing expired session: " + session.getId());
            return true;

        } catch (Exception e) {
            log.logError("Error handling session expiration", e);
            return false;
        }
    }

    private PaymentChargeData getChargeData(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            PaymentChargeData.PaymentChargeDataBuilder builder = PaymentChargeData.builder();

            String latestChargeId = paymentIntent.getLatestCharge();

            if (latestChargeId != null && !latestChargeId.isEmpty()) {
                Charge charge = Charge.retrieve(latestChargeId);
                updateBuilderFromCharge(builder, charge);

                if (paymentIntent.getPaymentMethod() != null) {
                    updateBuilderFromPaymentMethod(builder, paymentIntent.getPaymentMethod());
                }
                return builder.build();
            }

            ChargeCollection charges = Charge.list(
                    ChargeListParams.builder()
                            .setPaymentIntent(paymentIntentId)
                            .setLimit(1L)
                            .build()
            );

            if (charges.getData() != null && !charges.getData().isEmpty()) {
                Charge charge = charges.getData().get(0);
                updateBuilderFromCharge(builder, charge);

                if (paymentIntent.getPaymentMethod() != null) {
                    updateBuilderFromPaymentMethod(builder, paymentIntent.getPaymentMethod());
                }
            } else {
                if (paymentIntent.getPaymentMethod() != null) {
                    updateBuilderFromPaymentMethod(builder, paymentIntent.getPaymentMethod());
                }
            }

            return builder.build();

        } catch (StripeException e) {
            log.logError("Error retrieving charges for PaymentIntent: " + paymentIntentId, e);
            return PaymentChargeData.builder().build();
        }
    }

    private void updateBuilderFromCharge(PaymentChargeData.PaymentChargeDataBuilder builder, Charge charge) {
        try {
            if (charge.getReceiptUrl() != null && !charge.getReceiptUrl().isEmpty()) {
                builder.receiptUrl(charge.getReceiptUrl());
            }

            if (charge.getPaymentMethodDetails() != null) {
                var paymentMethodDetails = charge.getPaymentMethodDetails();

                if (paymentMethodDetails.getCard() != null) {
                    var card = paymentMethodDetails.getCard();
                    if (card.getBrand() != null) {
                        builder.cardBrand(card.getBrand());
                    }
                    if (card.getLast4() != null) {
                        builder.cardLastDigits(card.getLast4());
                    }
                }
            }

        } catch (Exception e) {
            log.logError("Error updating builder from charge: " + charge.getId(), e);
        }
    }


    private void updateBuilderFromPaymentMethod(PaymentChargeData.PaymentChargeDataBuilder builder, String paymentMethodId) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentMethodId);

            if (paymentMethod.getCard() != null) {
                var card = paymentMethod.getCard();
                if (card.getBrand() != null) {
                    builder.cardBrand(card.getBrand());
                }
                if (card.getLast4() != null) {
                    builder.cardLastDigits(card.getLast4());
                }
            }

        } catch (StripeException e) {
            log.logError("Error retrieving payment method: " + paymentMethodId, e);
        } catch (Exception e) {
            log.logError("Error processing payment method: " + paymentMethodId, e);
        }
    }

    private String getPaymentErrorMessage(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            if (paymentIntent.getLastPaymentError() != null) {
                return paymentIntent.getLastPaymentError().getMessage();
            }

            return null;

        } catch (StripeException e) {
            log.logError("Error retrieving PaymentIntent: " + paymentIntentId, e);
            return null;
        }
    }

    private JsonNode extractEventDataObject(Event event) throws Exception {
        String objectStr = event.getDataObjectDeserializer().getRawJson();

        if (objectStr == null || objectStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Event object JSON is null or empty");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode objectNode = objectMapper.readTree(objectStr);

        if (objectNode == null || objectNode.isNull()) {
            throw new IllegalArgumentException("Failed to parse event object JSON");
        }

        return objectNode;
    }

    private String getRequiredStringField(JsonNode node, String fieldName, String fieldDescription) throws IllegalArgumentException {
        JsonNode fieldNode = node.get(fieldName);
        if (fieldNode == null || fieldNode.isNull()) {
            throw new IllegalArgumentException(fieldDescription + " not found in JSON");
        }

        String value = fieldNode.asText();
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldDescription + " is null or empty");
        }

        return value;
    }

    private String getOptionalStringField(JsonNode node, String fieldName) {
        JsonNode fieldNode = node.get(fieldName);
        if (fieldNode == null || fieldNode.isNull()) {
            return null;
        }

        String value = fieldNode.asText();
        return (value != null && !value.trim().isEmpty()) ? value : null;
    }
}