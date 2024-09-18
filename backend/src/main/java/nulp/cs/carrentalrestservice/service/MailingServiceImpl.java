package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailSender;

    @Override
    public void sendEmail(String receiver, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSender);
            message.setTo(receiver);
            message.setSubject(subject);
            message.setText(text);
          javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
