package nulp.cs.carrentalrestservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.*;

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
