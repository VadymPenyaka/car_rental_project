package nulp.cs.carrentalrestservice.shared.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.modules.car.dto.DriveType;
import nulp.cs.carrentalrestservice.modules.car.dto.FuelType;
import nulp.cs.carrentalrestservice.modules.car.dto.GearboxType;

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
