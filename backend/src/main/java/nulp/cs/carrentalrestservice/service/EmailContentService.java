package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.event.EmailEvent;
import org.springframework.stereotype.Service;

@Service
public class EmailContentService {
    public String generateSubjectForStatusEmail(EmailEvent event) {
        return "Your order status has been changed to " +
                event.getCarOrder().getStatus().toString().toLowerCase() + "!";
    }

    public String generateBodyForStatusEmail(EmailEvent event) {
        return "Dear " + event.getCustomer().getFirstName()
                + ". The status of your order has been changed to "
                + event.getCarOrder().getStatus().toString().toLowerCase();
    }
}
