package nulp.cs.carrentalrestservice.modules.document.service;

import nulp.cs.carrentalrestservice.modules.document.dto.DocumentDTO;

import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    DocumentDTO createDocument (DocumentDTO documentDTO);

    void createRentalAgreementDocument(UUID orderId);

    Optional<DocumentDTO> getDocumentInfoById(UUID id);

    Optional<byte[]> getDocumentBytesById(UUID id);

    Optional<byte[]> signAgreement(UUID documentId);
}
