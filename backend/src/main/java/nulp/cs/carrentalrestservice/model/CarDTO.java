package nulp.cs.carrentalrestservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
