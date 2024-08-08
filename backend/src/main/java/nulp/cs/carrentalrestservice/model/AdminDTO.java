package nulp.cs.carrentalrestservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String password;
}
