package nulp.cs.carrentalrestservice.shared.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailingServiceImpl implements MailingService {
    @Value("${spring.mail.username}")
    private String mailSender;
    @Value("${sendgrid.api.key}")
    private String key;

    @Override
    @SneakyThrows
    public void sendEmail(String receiver, String subject, String text) {
        Email from = new Email(mailSender);
        Email toEmail = new Email(receiver);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(key);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        sg.api(request);
    }
}
