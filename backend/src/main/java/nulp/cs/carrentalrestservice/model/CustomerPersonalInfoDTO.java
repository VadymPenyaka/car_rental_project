package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerPersonalInfoDTO {
    private UUID id;
    private String passportId;
    private LocalDate birthDate;
    private LocalDate passportExpiryDate;
}
