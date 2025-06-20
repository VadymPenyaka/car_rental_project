package nulp.cs.carrentalrestservice.shared.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeRequest;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentEvent extends ApplicationEvent {
    private final StripeRequest stripeRequest;

    public PaymentEvent(Object source, StripeRequest stripeRequest) {
        super(source);
        this.stripeRequest = stripeRequest;
    }
}
