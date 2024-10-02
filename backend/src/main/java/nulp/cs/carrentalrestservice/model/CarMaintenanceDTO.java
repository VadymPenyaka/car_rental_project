package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarMaintenanceDTO {
    private Long id;
    private String description;
    private Double price;
    private CarDTO car;
    private CarScheduleDTO schedule;
}
