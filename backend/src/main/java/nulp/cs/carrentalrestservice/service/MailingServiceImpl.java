package nulp.cs.carrentalrestservice.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    private final static String SENDER_EMAIL = "vadym.peniaka.kn.2021@lpnu.ua";
    private final static String SENDER_PASSWORD = "herb whpq lcom slmj";

    private final static Session session;

    static {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(properties,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                    }
                });
    }

    public void sendEmail (String receiver, String subject, String text) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
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
