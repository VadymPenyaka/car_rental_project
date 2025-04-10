package nulp.cs.carrentalrestservice.service.document;

import nulp.cs.carrentalrestservice.model.dto.DocumentDTO;

import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    DocumentDTO createDocument (DocumentDTO documentDTO);

    Optional<DocumentDTO> getDocumentById (UUID id);

    Optional<DocumentDTO> updateDocumentById (UUID id, DocumentDTO documentDTO);
}
