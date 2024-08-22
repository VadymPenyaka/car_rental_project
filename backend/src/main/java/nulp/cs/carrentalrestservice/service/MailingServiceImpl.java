package nulp.cs.carrentalrestservice.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    @Value("${mail.sender.email}")
    private String senderEmail;
    @Value("${mail.sender.password}")
    private String senderPassword;
    private final Session session;
    @Autowired
    public MailingServiceImpl() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(properties,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });
    }

    public void sendEmail (String receiver, String subject, String text) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiver)
            );
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);


        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
