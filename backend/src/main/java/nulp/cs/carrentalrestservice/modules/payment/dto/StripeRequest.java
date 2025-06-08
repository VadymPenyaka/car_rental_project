package nulp.cs.carrentalrestservice.modules.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequest {
    private long amount;
    private String currency;
    private String name;
}
