package nulp.cs.carrentalrestservice.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Brand;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {
    private UUID id;
    private Brand brandName;
    private String modelName;
    private String description;
    private Integer year;
}
