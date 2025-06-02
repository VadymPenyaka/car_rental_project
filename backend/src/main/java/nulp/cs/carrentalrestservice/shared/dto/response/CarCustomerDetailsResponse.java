package nulp.cs.carrentalrestservice.shared.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.dto.LicenseCategory;
import nulp.cs.carrentalrestservice.modules.car.dto.*;
import nulp.cs.carrentalrestservice.modules.location.dto.LocationDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarCustomerDetailsResponse {
    private UUID id;
    private ModelDTO model;
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
    private LicenseCategory licenseCategory;
}
