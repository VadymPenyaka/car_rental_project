package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
        private long id;
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
