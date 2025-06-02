package nulp.cs.carrentalrestservice.modules.car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.dto.ScheduleStatus;
import nulp.cs.carrentalrestservice.modules.car.dto.CarDTO;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarScheduleDTO {
    private UUID id;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;
    private ScheduleStatus status;
}
