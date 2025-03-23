package nulp.cs.carrentalrestservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private UUID id;
    private CarClass carClass;
    private FuelType fuelType;
    private String brand;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocationDTO location;
    private GearboxType gearboxType;
}
