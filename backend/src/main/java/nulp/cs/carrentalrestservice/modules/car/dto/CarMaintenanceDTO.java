package nulp.cs.carrentalrestservice.modules.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.CarScheduleDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarMaintenanceDTO {
    private UUID id;
    private String description;
    private Double price;
    private CarScheduleDTO schedule;
}
