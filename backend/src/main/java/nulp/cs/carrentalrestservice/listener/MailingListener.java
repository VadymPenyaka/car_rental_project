package nulp.cs.carrentalrestservice.listener;

import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.service.MailingService;
import nulp.cs.carrentalrestservice.util.EmailContentCreator;
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

        mailingService.sendEmail(event.getCustomer().getEmail(), EmailContentCreator.generateSubjectForStatusEmail(event),
                EmailContentCreator.generateBodyForStatusEmail(event));
    }

}
