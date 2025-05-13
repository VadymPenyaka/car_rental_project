package nulp.cs.carrentalrestservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.DocumentType;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private UUID id;

    private DocumentType type;

    private Integer documentNumber;

    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
