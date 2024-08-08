package nulp.cs.carrentalrestservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarPricingDTO {
    private long id;
    private double pledge;
    private double upToThreeDays;
    private double upToTenDays;
    private double upToMonth;
    private double moreThenMonth;
}
