package nulp.cs.carrentalrestservice.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.event.OrderDocumentEvent;
import nulp.cs.carrentalrestservice.service.document.DocumentService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDocumentListener {
    private final DocumentService documentService;

    @EventListener
    public void createAndSaveAgreement (OrderDocumentEvent event) {
        documentService.createRentalAgreementDocument(event.getOrderId());
    }
}
