package nulp.cs.carrentalrestservice.modules.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.location.dto.LocationDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
        private UUID id;
        private ModelDTO model;
        private BodyType bodyType;
        private CarClass carClass;
        private FuelType fuelType;
        private GearboxType gearboxType;
        private DriveType driveType;
        private LocationDTO location;
        private CarPricingDTO carPricing;
        private CarDetailsDTO carDetails;
        private CarRegistrationInfoDTO registrationInfo;
}
