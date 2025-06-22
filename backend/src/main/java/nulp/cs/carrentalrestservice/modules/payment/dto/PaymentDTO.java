package nulp.cs.carrentalrestservice.modules.payment.dto;

import lombok.*;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private UUID id;

    private BigDecimal amount;

    private String currency;

    private PaymentStatus status;

    private String paymentIntentId;

    private String sessionId;

    private LocalDateTime createdAt;

    private LocalDateTime paidAt;

    private String cardBrand;

    private String cardLastDigits;

    private String receiptUrl;

    private CarOrderDTO carOrder;

    private String customerEmail;
}