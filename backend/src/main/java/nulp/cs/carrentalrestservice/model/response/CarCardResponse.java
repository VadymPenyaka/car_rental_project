package nulp.cs.carrentalrestservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.model.enumeration.DriveType;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarCardResponse {
    private UUID id;
    private String modelName;
    private String brandName;
    private int numberOfSeats;
    private FuelType fuelType;
    private int fuelConsumption;
    private DriveType driveType;
    private Double engineCapacity;
    private GearboxType gearboxType;
    private CarPricingDTO carPricing;
}
