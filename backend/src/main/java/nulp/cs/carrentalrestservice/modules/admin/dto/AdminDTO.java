package nulp.cs.carrentalrestservice.modules.admin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.location.dto.LocationDTO;
import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private UUID id;
    private String position;
    @Valid
    @NotNull(message = "Credentials can not be empty!")
    private PersonDTO person;
    private String department;
    private boolean isOnVocation;
    private LocationDTO location;
}
