package nulp.cs.carrentalrestservice.listener;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.service.MailingService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MailingListener {
    private final MailingService mailingService;

    public MailingListener(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @EventListener
    public void handleEmailEvent (EmailEvent event) {
        String subject = "Your order status has been changed to " +
                event.getCarOrder().getStatus().toString().toLowerCase() + "!";

        String text = "Dear " + event.getCustomer().getFirstName()
                + ". The status of your order has been changed to "
                + event.getCarOrder().getStatus().toString().toLowerCase()+".";

        mailingService.sendEmail(event.getCustomer().getEmail(), subject, text);
    }

}
