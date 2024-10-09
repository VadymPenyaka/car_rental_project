package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderDTO {
    private UUID id;
    private AdminDTO admin;
    private OrderStatus status;
    private CustomerDTO customer;
    private double totalPrice;
    private String comment;
    private CarScheduleDTO schedule;
}
