package nulp.cs.carrentalrestservice.modules.document.mapper;

import nulp.cs.carrentalrestservice.modules.document.enity.Document;
import nulp.cs.carrentalrestservice.modules.document.dto.DocumentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    Document documentDtoToDocument (DocumentDTO documentDTO);

    DocumentDTO documentToDocumentDto (Document document);
}
