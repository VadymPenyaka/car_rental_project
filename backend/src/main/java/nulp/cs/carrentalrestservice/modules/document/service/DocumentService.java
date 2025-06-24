package nulp.cs.carrentalrestservice.modules.document.service;

import nulp.cs.carrentalrestservice.modules.document.dto.DocumentDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.shared.dto.response.DocumentInfoResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    DocumentDTO createDocument (DocumentDTO documentDTO);

    void createRentalAgreementDocument(CarOrderDTO order);

    Optional<DocumentDTO> getDocumentInfoById(UUID id);

    Optional<byte[]> getDocumentBytesById(UUID id);

    Optional<byte[]> signAgreement(UUID documentId);

    List<DocumentInfoResponse> getUnsignedDocumentsByPersonId(UUID personId);
}
