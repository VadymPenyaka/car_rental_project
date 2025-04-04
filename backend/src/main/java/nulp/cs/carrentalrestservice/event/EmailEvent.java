package nulp.cs.carrentalrestservice.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.CustomerDTO;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailEvent extends ApplicationEvent {

    private final CarOrderDTO carOrder;
    private final CustomerDTO customer;

    public EmailEvent(Object source, CarOrderDTO carOrder, CustomerDTO customer) {
        super(source);
        this.carOrder = carOrder;
        this.customer = customer;
    }

}
