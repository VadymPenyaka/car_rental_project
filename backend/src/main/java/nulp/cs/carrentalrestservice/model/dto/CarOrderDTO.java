package nulp.cs.carrentalrestservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Person;
import nulp.cs.carrentalrestservice.model.enumeration.OrderStatus;

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
