package nulp.cs.carrentalrestservice.modules.payment.controller;

import nulp.cs.carrentalrestservice.shared.dto.response.PaymentStatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(PaymentController.BASE_PATH)
public class PaymentController {
    public final static String BASE_PATH = "/api/v1/payments";

    @GetMapping("/success")
    @ResponseBody
    public ResponseEntity<PaymentStatusResponse> handleSuccessfulPayment(
            @RequestParam("session_id") String sessionId) {

        PaymentStatusResponse response = new PaymentStatusResponse(
                "success",
                "Your order has been successfully paid",
                sessionId
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancel")
    @ResponseBody
    public ResponseEntity<PaymentStatusResponse> handleCancelledPayment(
            @RequestParam("session_id") String sessionId) {

        PaymentStatusResponse response = new PaymentStatusResponse(
                "cancelled",
                "Your order has not been paid",
                sessionId
        );

        return ResponseEntity.ok(response);
    }
}
