package nulp.cs.carrentalrestservice.modules.bankid.dto;


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
public class DriverLicenseCategoryDTO {
    private UUID id;
    private LicenseCategory category;
    private LocalDate issueDate;
}
