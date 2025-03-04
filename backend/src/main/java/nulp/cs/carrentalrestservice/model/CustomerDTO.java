package nulp.cs.carrentalrestservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private UUID id;
    @Valid
    @NotNull(message = "Credentials can not be empty!")
    private PersonDTO person;
    @Valid
    @NotNull(message = "Passport credentials is required!")
    private PassportDTO passport;
    @Valid
    @NotNull(message = "Driver license credentials is required!")
    private DriverLicensesDTO driverLicenses;
}
