package nulp.cs.carrentalrestservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchRequest {
    private String city;
    private CarClass carClass;
    private GearboxType gearboxType;
    private FuelType fuelType;
    private Double minPrice;
    private Double maxPrice;
    private String brand;
    private LocalDate startDate;
    private LocalDate endDate;
}
