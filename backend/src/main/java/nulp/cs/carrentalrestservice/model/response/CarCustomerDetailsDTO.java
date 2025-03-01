package nulp.cs.carrentalrestservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.model.enumeration.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarCustomerDetailsDTO {
    private UUID id;
    private String brand;
    private String model;
    private String color;
    private CarClass carClass;
    private BodyType bodyType;
    private int numberOfSeats;
    private FuelType fuelType;
    private int trunkCapacity;
    private DriveType driveType;
    private int fuelConsumption;
    private LocationDTO location;
    private int fuelTankCapacity;
    private double engineCapacity;
    private GearboxType gearboxType;
    private CarPricingDTO carPricing;
}
