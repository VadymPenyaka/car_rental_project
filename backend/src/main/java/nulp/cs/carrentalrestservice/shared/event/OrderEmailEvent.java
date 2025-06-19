package nulp.cs.carrentalrestservice.shared.event;


import lombok.Getter;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
import nulp.cs.carrentalrestservice.modules.person.entity.Person;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderEmailEvent extends ApplicationEvent {

    private final CarOrderDTO carOrder;
    private final Person person;

    public OrderEmailEvent(Object source, CarOrderDTO carOrder, Person person) {
        super(source);
        this.carOrder = carOrder;
        this.person = person;
    }

}
