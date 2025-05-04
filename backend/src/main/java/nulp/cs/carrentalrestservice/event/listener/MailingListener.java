package nulp.cs.carrentalrestservice.event.listener;

import jakarta.validation.Valid;
import nulp.cs.carrentalrestservice.event.OrderEmailEvent;
import nulp.cs.carrentalrestservice.event.VerificationEmailEvent;
import nulp.cs.carrentalrestservice.util.mail.MailingService;
import nulp.cs.carrentalrestservice.util.mail.EmailContentCreator;
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
        mailingService.sendEmail(event.getCustomer().getPerson().getUsername(), EmailContentCreator.generateSubjectForStatusEmail(event),
                EmailContentCreator.generateBodyForStatusEmail(event));
    }

    @EventListener
    public void handleTokenCreatedEvent(VerificationEmailEvent event) {
        String confirmationUrl = domain+"/api/v1/verify?token=" + event.getToken();
        String message = "You change " +event.getVerificationType().name() +
                ".\nTo confirm the change click the following link:\n" + confirmationUrl;

        mailingService.sendEmail(event.getRecipientEmail(), "Confirmation Required", message);
    }

}
