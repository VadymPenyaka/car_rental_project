package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarScheduleDTO {
    private Long id;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;
    private ScheduleStatus status;
}
