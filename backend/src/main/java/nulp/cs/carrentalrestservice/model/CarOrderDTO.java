package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderDTO {
    private long id;
    private AdminDTO admin;
    private OrderStatus status;
    private CustomerDTO customer;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer serviceDuration;
    private double totalPrice;
    private String comment;
}
