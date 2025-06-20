package nulp.cs.carrentalrestservice.shared.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class OrderStatusEvent extends ApplicationEvent {
    private final UUID orderId;
    private final OrderStatus orderStatus;

    public OrderStatusEvent(Object source, UUID orderId, OrderStatus orderStatus) {
        super(source);
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
