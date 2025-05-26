package nulp.cs.carrentalrestservice.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.PersonalDataDTO;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderEmailEvent extends ApplicationEvent {

    private final CarOrderDTO carOrder;
    private final PersonalDataDTO customer;

    public OrderEmailEvent(Object source, CarOrderDTO carOrder, PersonalDataDTO customer) {
        super(source);
        this.carOrder = carOrder;
        this.customer = customer;
    }

}
