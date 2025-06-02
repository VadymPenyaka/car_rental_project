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
public class CarRegistrationInfoDTO {
    private UUID id;
    private String vin;
    private String number;
    private String color;
    private String passportId;
    private String insurance_id;
}
