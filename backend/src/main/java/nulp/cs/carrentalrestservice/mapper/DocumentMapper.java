package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Document;
import nulp.cs.carrentalrestservice.model.dto.DocumentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    Document documentDtoToDocument (DocumentDTO documentDTO);

    DocumentDTO documentToDocumentDto (Document document);
}
