package nulp.cs.carrentalrestservice.util.mail;

import java.io.IOException;

public interface MailingService {
    void sendEmail(String receiver, String subject, String text);
}
