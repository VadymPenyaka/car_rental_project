package nulp.cs.carrentalrestservice.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.shared.annotation.EndDate;
import nulp.cs.carrentalrestservice.shared.annotation.StartDate;
import nulp.cs.carrentalrestservice.shared.annotation.ValidOrderPeriod;
import nulp.cs.carrentalrestservice.modules.car.dto.CarClass;
import nulp.cs.carrentalrestservice.modules.car.dto.FuelType;
import nulp.cs.carrentalrestservice.modules.car.dto.GearboxType;

import java.time.LocalDate;

@Data
@Builder
@ValidOrderPeriod
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
    @StartDate
    private LocalDate startDate;
    @EndDate
    private LocalDate endDate;
}
