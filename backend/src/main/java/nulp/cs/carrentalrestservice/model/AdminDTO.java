package nulp.cs.carrentalrestservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.ValidPassword;
import nulp.cs.carrentalrestservice.model.enumeration.Role;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private UUID id;
}
