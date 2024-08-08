package nulp.cs.carrentalrestservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrderDetailDTO {
    private  long id;
    private CarDTO car;
    private CustomerDTO customer;
    private int numberOfDays;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private String pickUpLocation;
    private String dropOffLocation;
    private double totalPrice;
    private String comment;
}
