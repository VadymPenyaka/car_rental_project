package nulp.cs.carrentalrestservice.model.dto;

import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.PaymentMethod;
import nulp.cs.carrentalrestservice.model.enumeration.PaymentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private UUID id;

    private double amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDate = LocalDateTime.now();

    private CarOrderDTO carOrder;
}
