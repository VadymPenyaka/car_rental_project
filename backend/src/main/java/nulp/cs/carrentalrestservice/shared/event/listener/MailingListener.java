package nulp.cs.carrentalrestservice.shared.event.listener;

import nulp.cs.carrentalrestservice.modules.person.dto.VerificationType;
import nulp.cs.carrentalrestservice.shared.event.OrderEmailEvent;
import nulp.cs.carrentalrestservice.shared.event.PaymentStatusEvent;
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

    @EventListener
    public void handlePaymentEmailEvent(PaymentStatusEvent event) {
        String subject;
        String message;

        switch (event.getPaymentStatus()) {
            case PAID -> {
                subject = "Payment Successful - 5Cars Rental";
                message = "Dear Customer,\n\n" +
                        "Your payment has been successfully processed!\n\n" +
                        "Your car rental order has been confirmed. Please visit your personal account to review and sign the required documents to complete your rental agreement.\n\n" +
                        "Thank you for choosing 5Cars!\n\n" +
                        "Best regards,\n" +
                        "5Cars Team";
            }
            case FAILED -> {
                subject = "Payment Failed - 5Cars Rental";
                message = "Dear Customer,\n\n" +
                        "Unfortunately, your payment could not be processed.\n\n" +
                        "Please check your payment details and try again. If the problem persists, contact your bank or try a different payment method.\n\n" +
                        "If you need assistance, please contact our support team.\n\n" +
                        "Best regards,\n" +
                        "5Cars Team";
            }
            case PENDING -> {
                subject = "Payment Pending - 5Cars Rental";
                message = "Dear Customer,\n\n" +
                        "Your payment is currently being processed.\n\n" +
                        "This may take a few minutes to complete. You will receive a confirmation email once the payment is successful.\n\n" +
                        "If you have any questions, please contact our support team.\n\n" +
                        "Best regards,\n" +
                        "5Cars Team";
            }
            case REFUNDED -> {
                subject = "Payment Refunded - 5Cars Rental";
                message = "Dear Customer,\n\n" +
                        "Your payment has been refunded.\n\n" +
                        "The refund will appear in your account within 3-5 business days depending on your payment method.\n\n" +
                        "If you have any questions about this refund, please contact our support team.\n\n" +
                        "Best regards,\n" +
                        "5Cars Team";
            }
            default -> {
                subject = "Payment Update - 5Cars Rental";
                message = "Dear Customer,\n\n" +
                        "There has been an update to your payment status.\n\n" +
                        "Please contact our support team for more information.\n\n" +
                        "Best regards,\n" +
                        "5Cars Team";
            }
        }

        mailingService.sendEmail(event.getCustomerEmail(), subject, message);
    }


}
