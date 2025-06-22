package nulp.cs.carrentalrestservice.modules.payment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentChargeData {
    private String receiptUrl;
    private String cardBrand;
    private String cardLastDigits;
    private String errorMessage;
}
