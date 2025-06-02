package nulp.cs.carrentalrestservice.modules.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO {
    private UUID id;
    private BrandDTO brandName;
    private String modelName;
    private String description;
    private Integer year;
}
