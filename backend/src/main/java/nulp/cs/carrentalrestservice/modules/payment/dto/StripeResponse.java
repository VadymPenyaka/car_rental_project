package nulp.cs.carrentalrestservice.modules.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeResponse {
    private String message;
    private String status;
    private String sessionId;
    private String url;
}
