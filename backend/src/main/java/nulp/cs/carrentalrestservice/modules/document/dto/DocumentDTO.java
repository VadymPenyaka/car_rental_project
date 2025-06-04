package nulp.cs.carrentalrestservice.modules.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private UUID id;

    private DocumentType type;

    private Integer documentNumber;

    private CarOrderDTO order;

    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
