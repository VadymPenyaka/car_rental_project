package nulp.cs.carrentalrestservice.modules.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nulp.cs.carrentalrestservice.modules.payment.service.StripeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final StripeService stripeService;


    @PostMapping("/stripe")
    public ResponseEntity<Map<String, Object>> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String sigHeader) {

        long startTime = System.currentTimeMillis();

        try {
            log.info("Received Stripe webhook at {}", LocalDateTime.now());
            log.debug("Payload size: {} bytes", payload.length());

            boolean processed = stripeService.processWebhookEvent(payload, sigHeader);

            long processingTime = System.currentTimeMillis() - startTime;

            Map<String, Object> response = Map.of(
                    "received", true,
                    "processed", processed,
                    "processingTimeMs", processingTime,
                    "timestamp", LocalDateTime.now()
            );

            if (processed) {
                log.info("Webhook processed successfully in {}ms", processingTime);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Webhook processing failed in {}ms", processingTime);
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            long processingTime = System.currentTimeMillis() - startTime;
            log.error("Webhook processing error after {}ms: {}", processingTime, e.getMessage(), e);

            return ResponseEntity.badRequest().body(Map.of(
                    "received", false,
                    "processed", false,
                    "error", e.getMessage(),
                    "processingTimeMs", processingTime,
                    "timestamp", LocalDateTime.now()
            ));
        }
    }

    @GetMapping("/stripe/health")
    public ResponseEntity<Map<String, Object>> webhookHealthCheck() {
        return ResponseEntity.ok(Map.of(
                "status", "healthy",
                "service", "stripe-webhook",
                "timestamp", LocalDateTime.now()
        ));
    }

}
