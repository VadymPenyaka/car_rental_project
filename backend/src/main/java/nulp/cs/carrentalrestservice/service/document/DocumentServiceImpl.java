package nulp.cs.carrentalrestservice.service.document;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.DocumentMapper;
import nulp.cs.carrentalrestservice.model.dto.DocumentDTO;
import nulp.cs.carrentalrestservice.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO) {
        return documentMapper.documentToDocumentDto(documentRepository
                .save(documentMapper.documentDtoToDocument(documentDTO)));
    }

    @Override
    public Optional<DocumentDTO> getDocumentById(UUID id) {
        return Optional.ofNullable(documentMapper
                .documentToDocumentDto(documentRepository
                        .findById(id).orElse(null)));
    }

    @Override
    public Optional<DocumentDTO> updateDocumentById(UUID id, DocumentDTO documentDTO) {
        AtomicReference<Optional<DocumentDTO>> atomicReference = new AtomicReference<>();

        documentRepository.findById(id).ifPresentOrElse ( foundDocument -> {
                foundDocument.setFilePath(documentDTO.getFilePath());
                foundDocument.setType(documentDTO.getType());
                foundDocument.setCreatedAt(documentDTO.getCreatedAt());

                atomicReference.set(Optional.ofNullable(documentMapper
                        .documentToDocumentDto(documentRepository.save(foundDocument))));

            }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}
