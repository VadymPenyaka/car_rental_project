package nulp.cs.carrentalrestservice.shared.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.service.OrderService;
import nulp.cs.carrentalrestservice.shared.event.OrderStatusEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusListener {
    private final OrderService orderService;

    @EventListener
    public void handleOrderStatusChangedEvent(OrderStatusEvent event) {
        orderService.updateOrderStatusById(event.getOrderId(), event.getOrderStatus());
    }
}
