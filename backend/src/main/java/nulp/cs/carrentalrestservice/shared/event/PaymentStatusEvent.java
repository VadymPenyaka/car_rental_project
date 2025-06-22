package nulp.cs.carrentalrestservice.shared.event;


import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentStatus;
import org.springframework.context.ApplicationEvent;

@Getter
public class PaymentStatusEvent extends ApplicationEvent {
    private final PaymentStatus paymentStatus;
    private final String customerEmail;

    public PaymentStatusEvent(Object source, PaymentStatus paymentStatus, String customerEmail) {
        super(source);
        this.paymentStatus = paymentStatus;
        this.customerEmail = customerEmail;
    }
}
