package nulp.cs.carrentalrestservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniqueDriverLicenseNumber;
import nulp.cs.carrentalrestservice.annotation.ValidExpiryDate;
import nulp.cs.carrentalrestservice.entity.Document;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLicensesDTO {
    private UUID id;
    @NotNull(message = "Category is mandatory!")
    private Set<DriverLicenseCategoryDTO> categories;
    @NotNull(message = "Issue date is mandatory!")
    private LocalDate issueDate;
    @ValidExpiryDate
    private LocalDate expirationDate;
    @NotBlank(message = "Authority code is mandatory!")
    @Size(min = 6, max = 6, message = "Must be 6 digit length!")
    private String issuedBy;
    @UniqueDriverLicenseNumber
    @NotBlank(message = "Password ID is mandatory!")
    @Size(min = 9, max = 9, message = "Must be 9 digit length!")
    private String documentNumber;
}
