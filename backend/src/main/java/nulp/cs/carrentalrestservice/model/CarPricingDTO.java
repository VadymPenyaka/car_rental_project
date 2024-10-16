package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPricingDTO {
    private UUID id;
    private double pledge;
    private double upToThreeDays;
    private double upToTenDays;
    private double upToMonth;
    private double moreThenMonth;
}
