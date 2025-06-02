package nulp.cs.carrentalrestservice.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequest {
    private UUID carId;
    private LocalDate startDate;
    private LocalDate endDate;
}
