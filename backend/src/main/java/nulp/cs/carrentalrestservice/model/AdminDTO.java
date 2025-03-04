package nulp.cs.carrentalrestservice.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
