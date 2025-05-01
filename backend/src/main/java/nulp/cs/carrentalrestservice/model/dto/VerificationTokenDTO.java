package nulp.cs.carrentalrestservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.ChangeType;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationTokenDTO {
    private String token;
    private ChangeType type;
    private String value;
    private LocalDateTime expiryDate;
    private PersonDTO person;

}
