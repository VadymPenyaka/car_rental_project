package nulp.cs.carrentalrestservice.modules.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.admin.dto.AdminDTO;
import nulp.cs.carrentalrestservice.modules.car.CarScheduleDTO;
import nulp.cs.carrentalrestservice.modules.person.PersonDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderDTO {
    private UUID id;
    private AdminDTO admin;
    private OrderStatus status;
    private PersonDTO person;
    private double totalPrice;
    private String comment;
    private CarScheduleDTO schedule;
}
