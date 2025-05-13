package nulp.cs.carrentalrestservice.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class OrderDocumentEvent extends ApplicationEvent {
    private final UUID orderId;


    public OrderDocumentEvent(Object source, UUID orderId) {
        super(source);
        this.orderId = orderId;
    }
}

