package nulp.cs.carrentalrestservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.VerificationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationTokenDTO {
    private UUID token;
    private VerificationType type;
    private String value;
    private LocalDateTime expiryDate;
    private PersonDTO person;

}
