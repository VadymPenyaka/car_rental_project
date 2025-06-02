package nulp.cs.carrentalrestservice.shared.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateMaintenanceEvent extends ApplicationEvent {
    private final CarOrderDTO carOrder;

    public CreateMaintenanceEvent(Object source, CarOrderDTO carOrder) {
        super(source);
        this.carOrder = carOrder;
    }
}
