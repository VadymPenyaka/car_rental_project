package nulp.cs.carrentalrestservice.shared.event;


import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
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
