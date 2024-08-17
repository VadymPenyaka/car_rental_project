package nulp.cs.carrentalrestservice.listener;

import nulp.cs.carrentalrestservice.event.EmailEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MailingListener {

    @EventListener
    public void handleEmailEvent (EmailEvent event) {
        
    }

}
