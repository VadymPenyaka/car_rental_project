package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.DocumentDTO;

import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    DocumentDTO createDocument (DocumentDTO documentDTO);

    Optional<DocumentDTO> getDocumentById (UUID id);

    Optional<DocumentDTO> updateDocumentById (UUID id, DocumentDTO documentDTO);
}
