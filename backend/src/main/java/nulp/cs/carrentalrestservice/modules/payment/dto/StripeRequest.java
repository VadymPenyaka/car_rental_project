package nulp.cs.carrentalrestservice.modules.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequest {
    private BigDecimal amount;
    private String currency;
    private String name;
    private CarOrder carOrder;
}
