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
        private double pricePerDay;
        private CarClass carClass;
        private boolean isAvailable;
        private int fuelConsumption;
        private int numberOfSeats;
        private String location;
        private FuelType fuelType;
        private GearboxType gearboxType;
}
