package nulp.cs.carrentalrestservice.shared.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.shared.annotation.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonRequest {
    @ValidPassword
    private String password;
    @ValidEmail
    @UniqueEmail
    private String email;
    @ValidPhoneNumber
    @UniquePhoneNumber
    private String phoneNumber;
}
