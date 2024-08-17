package nulp.cs.carrentalrestservice.service;

public interface MailingService {
    void sendEmail(String receiver, String subject, String text);
}
