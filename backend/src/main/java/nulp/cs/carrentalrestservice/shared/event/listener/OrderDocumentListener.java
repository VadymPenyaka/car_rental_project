package nulp.cs.carrentalrestservice.shared.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.event.OrderDocumentEvent;
import nulp.cs.carrentalrestservice.modules.document.service.DocumentService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDocumentListener {
    private final DocumentService documentService;

    @EventListener
    public void createAndSaveAgreement (OrderDocumentEvent event) {
        documentService.createRentalAgreementDocument(event.getOrder());
    }
}
