package nulp.cs.carrentalrestservice.event;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import org.springframework.context.ApplicationEvent;

public class EmailEvent extends ApplicationEvent {

    private CarOrderDTO carOrder;
    private CustomerDTO customer;

    public EmailEvent(Object source, CarOrderDTO carOrder, CustomerDTO customer) {
        super(source);
        this.carOrder = carOrder;
        this.customer = customer;
    }

    public CarOrderDTO getCarOrder() {
        return carOrder;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }
}
