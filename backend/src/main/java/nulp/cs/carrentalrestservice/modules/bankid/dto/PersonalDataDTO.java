package nulp.cs.carrentalrestservice.modules.bankid.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalDataDTO {
    private UUID id;
    @Valid
    @NotNull(message = "Credentials can not be empty!")
    private PersonDTO person;
    @Valid
    @NotNull(message = "Passport credentials is required!")
    private PassportDTO passport;
    @Valid
    @NotNull(message = "Driver license credentials is required!")
    private DriverLicensesDTO driverLicense;
}
