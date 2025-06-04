package nulp.cs.carrentalrestservice.shared.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class OrderDocumentEvent extends ApplicationEvent {
    private final CarOrderDTO order;


    public OrderDocumentEvent(Object source, CarOrderDTO carOrder) {
        super(source);
        this.order = carOrder;
    }
}

