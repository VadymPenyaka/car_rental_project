package nulp.cs.carrentalrestservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.dto.BrandDTO;
import nulp.cs.carrentalrestservice.model.dto.LocationDTO;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchRequestDto {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private CarClass carClass;
    private GearboxType gearboxType;
    private FuelType fuelType;
    private String brand;
    private Double minPrice;
    private Double maxPrice;
}
