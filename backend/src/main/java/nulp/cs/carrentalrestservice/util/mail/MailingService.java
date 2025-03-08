package nulp.cs.carrentalrestservice.util.mail;

public interface MailingService {
    void sendEmail(String receiver, String subject, String text);
}
