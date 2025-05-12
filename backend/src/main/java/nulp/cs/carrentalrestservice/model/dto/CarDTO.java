package nulp.cs.carrentalrestservice.model.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.entity.*;
import nulp.cs.carrentalrestservice.model.enumeration.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
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

//        private UUID id;
//        private String vin;
//        private String color;
//        private ModelDTO model;
//        private GearboxType gearboxType;
//        private String number;
//        private BodyType bodyType;
//        private CarClass carClass;
//        private int numberOfSeats;
//        private FuelType fuelType;
//        private int trunkCapacity;
//        private int fuelConsumption;
//        private CarPricingDTO carPricing;
//        private DriveType driveType;
//        private LocationDTO location;
//        private int fuelTankCapacity;
//        private double engineCapacity;
//        private int requiredExperience;
//        private LicenseCategory licenseCategory;
}
