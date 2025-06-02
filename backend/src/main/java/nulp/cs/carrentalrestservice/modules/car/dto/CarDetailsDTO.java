package nulp.cs.carrentalrestservice.modules.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.dto.LicenseCategory;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDetailsDTO {
    private UUID id;
    private Double engineCapacity;
    private int fuelTankCapacity;
    private int trunkCapacity;
    private Integer fuelConsumption;
    private Integer numberOfSeats;
    private LicenseCategory licenseCategory;
    private int requiredExperience;
    private List<String> configuration;
}
