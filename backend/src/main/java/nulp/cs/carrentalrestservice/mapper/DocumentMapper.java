package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Document;
import nulp.cs.carrentalrestservice.model.DocumentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface DocumentMapper {
    Document documentDtoToDocument (DocumentDTO documentDTO);

    DocumentDTO documentToDocumentDto (Document document);
}
