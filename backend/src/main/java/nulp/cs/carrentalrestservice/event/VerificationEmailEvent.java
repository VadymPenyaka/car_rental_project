package nulp.cs.carrentalrestservice.event;

import lombok.Getter;
import nulp.cs.carrentalrestservice.model.enumeration.VerificationType;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public class VerificationEmailEvent extends ApplicationEvent {
    private final UUID token;
    private final String recipientEmail;
    private final VerificationType verificationType;

    public VerificationEmailEvent(Object source, UUID token, String recipientEmail, VerificationType verificationType) {
        super(source);
        this.token = token;
        this.recipientEmail = recipientEmail;
        this.verificationType = verificationType;
    }

}
