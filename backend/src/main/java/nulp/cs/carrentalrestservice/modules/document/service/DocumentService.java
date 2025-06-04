package nulp.cs.carrentalrestservice.modules.document.service;

import nulp.cs.carrentalrestservice.modules.document.dto.DocumentDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;

import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    DocumentDTO createDocument (DocumentDTO documentDTO);

    void createRentalAgreementDocument(CarOrderDTO order);

    Optional<DocumentDTO> getDocumentInfoById(UUID id);

    Optional<byte[]> getDocumentBytesById(UUID id);

    Optional<byte[]> signAgreement(UUID documentId);
}
