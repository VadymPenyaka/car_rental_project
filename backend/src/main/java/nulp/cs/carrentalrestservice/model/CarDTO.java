package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.enumeration.DriveType;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
        private UUID id;
        private String brand;
        private String model;
        private CarClass carClass;
        private int fuelConsumption;
        private int numberOfSeats;
        private FuelType fuelType;
        private GearboxType gearboxType;
        private LocationDTO location;
        private DriveType driveType;
        private Double engineCapacity;
}
