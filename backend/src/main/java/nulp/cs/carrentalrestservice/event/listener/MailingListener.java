package nulp.cs.carrentalrestservice.event.listener;

import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.util.mail.MailingService;
import nulp.cs.carrentalrestservice.util.mail.EmailContentCreator;
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

        mailingService.sendEmail(event.getCustomer().getPerson().getUsername(), EmailContentCreator.generateSubjectForStatusEmail(event),
                EmailContentCreator.generateBodyForStatusEmail(event));
    }

}
