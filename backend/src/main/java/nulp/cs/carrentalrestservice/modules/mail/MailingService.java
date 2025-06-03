package nulp.cs.carrentalrestservice.modules.mail;

import java.io.IOException;

public interface MailingService {
    void sendEmail(String receiver, String subject, String text);
}
