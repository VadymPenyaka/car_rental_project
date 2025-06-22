package nulp.cs.carrentalrestservice.shared.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.payment.service.PaymentService;
import nulp.cs.carrentalrestservice.modules.payment.service.StripeService;
import nulp.cs.carrentalrestservice.shared.event.PaymentEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {
    private final StripeService stripeService;

    @EventListener
    public void handlePaymentSuccess(PaymentEvent event) {
        stripeService.createPaymentLink(event.getStripeRequest());
    }
}
