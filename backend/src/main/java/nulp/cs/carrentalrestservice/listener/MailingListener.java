package nulp.cs.carrentalrestservice.listener;

import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.service.MailingService;
import nulp.cs.carrentalrestservice.service.EmailContentService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MailingListener {
    private final MailingService mailingService;
    private final EmailContentService emailContentService;
    public MailingListener(MailingService mailingService, EmailContentService emailContentService) {
        this.mailingService = mailingService;
        this.emailContentService = emailContentService;
    }


    @EventListener
    public void handleEmailEvent (EmailEvent event) {

        mailingService.sendEmail(event.getCustomer().getEmail(),emailContentService.generateSubjectForStatusEmail(event),
                emailContentService.generateBodyForStatusEmail(event));
    }

}
