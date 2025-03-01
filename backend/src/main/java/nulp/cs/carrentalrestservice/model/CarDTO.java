package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
        private UUID id;
        private int year;
        private String vin;
        private String color;
        private GearboxType gearboxType;
        private String brand;
        private String model;
        private String number;
        private BodyType bodyType;
        private CarClass carClass;
        private int numberOfSeats;
        private FuelType fuelType;
        private int trunkCapacity;
        private int fuelConsumption;
        private CarPricingDTO carPricing;
        private DriveType driveType;
        private LocationDTO location;
        private int fuelTankCapacity;
        private double engineCapacity;
}
