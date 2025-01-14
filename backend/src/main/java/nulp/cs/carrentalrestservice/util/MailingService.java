package nulp.cs.carrentalrestservice.util;

public interface MailingService {
    void sendEmail(String receiver, String subject, String text);
}
