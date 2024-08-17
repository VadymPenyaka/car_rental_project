package nulp.cs.carrentalrestservice.event;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.Customer;
import org.springframework.context.ApplicationEvent;

public class EmailEvent extends ApplicationEvent {

    private CarOrder carOrder;
    private Customer customer;

    public EmailEvent(Object source, CarOrder carOrder, Customer customer) {
        super(source);
        this.carOrder = carOrder;
        this.customer = customer;
    }

    public CarOrder getCarOrder() {
        return carOrder;
    }

    public Customer getCustomer() {
        return customer;
    }
}
