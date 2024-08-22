package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    private  long id;
    private CarDTO car;
    private int numberOfDays;
    private LocalDate pickUpDate;
    private LocalDate dropOffDate;
    private String pickUpLocation;
    private String dropOffLocation;
    private double totalPrice;
    private String comment;
}
