package nulp.cs.carrentalrestservice.shared.event.listener;

import nulp.cs.carrentalrestservice.modules.person.dto.VerificationType;
import nulp.cs.carrentalrestservice.shared.event.OrderEmailEvent;
import nulp.cs.carrentalrestservice.shared.event.VerificationEmailEvent;
import nulp.cs.carrentalrestservice.modules.mail.EmailContentCreator;
import nulp.cs.carrentalrestservice.modules.mail.MailingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MailingListener {
    private final MailingService mailingService;
    @Value("${mail.domain}")
    private String domain;

    public MailingListener(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @EventListener
    public void handleEmailEvent (OrderEmailEvent event) {
        mailingService.sendEmail(event.getPerson().getUsername(), EmailContentCreator.generateSubjectForStatusEmail(event),
                EmailContentCreator.generateBodyForStatusEmail(event));
    }

    @EventListener
    public void handleTokenCreatedEvent(VerificationEmailEvent event) {
        String confirmationUrl;
        String message;
        if (event.getVerificationType()== VerificationType.REGISTRATION) {
            confirmationUrl = domain+"/api/v1/verify/registration?token=" + event.getToken();
            message = "To confirm registration on 5Cars click on the following link:\n" + confirmationUrl;

        } else {
            confirmationUrl = domain + "/api/v1/verify/change?token=" + event.getToken();
            message = "You change " + event.getVerificationType().name() +
                    ".\nTo confirm the change click on the following link:\n" + confirmationUrl;
        }
        mailingService.sendEmail(event.getRecipientEmail(), "Confirmation Required", message);
    }

}
