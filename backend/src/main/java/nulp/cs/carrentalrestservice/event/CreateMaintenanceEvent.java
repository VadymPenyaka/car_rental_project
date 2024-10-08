package nulp.cs.carrentalrestservice.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import org.springframework.context.ApplicationEvent;

@Getter
public class CreateMaintenanceEvent extends ApplicationEvent {
    private final CarOrderDTO carOrder;

    public CreateMaintenanceEvent(Object source, CarOrderDTO carOrder) {
        super(source);
        this.carOrder = carOrder;
    }
}
