package nulp.cs.carrentalrestservice.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.shared.annotation.EndDate;
import nulp.cs.carrentalrestservice.shared.annotation.StartDate;
import nulp.cs.carrentalrestservice.shared.annotation.ValidOrderPeriod;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@ValidOrderPeriod
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {
    private UUID carId;
    @StartDate
    private LocalDate startDate;
    @EndDate
    private LocalDate endDate;
}
