package nulp.cs.carrentalrestservice.shared.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.dto.DriverLicensesDTO;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PassportDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfoRequest {
    private UUID personId;
    @Valid
    @NotNull(message = "Passport credentials is required!")
    private PassportDTO passport;
    @Valid
    @NotNull(message = "Driver license credentials is required!")
    private DriverLicensesDTO driverLicense;
}
