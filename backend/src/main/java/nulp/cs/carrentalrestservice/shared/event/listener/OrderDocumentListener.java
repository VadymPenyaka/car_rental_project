package nulp.cs.carrentalrestservice.shared.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.event.OrderDocumentEvent;
import nulp.cs.carrentalrestservice.modules.document.service.DocumentService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderDocumentListener {
    private final DocumentService documentService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void createAndSaveAgreement (OrderDocumentEvent event) {
        documentService.createRentalAgreementDocument(event.getOrder());
    }
}
