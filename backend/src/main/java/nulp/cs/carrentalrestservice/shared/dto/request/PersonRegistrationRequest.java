package nulp.cs.carrentalrestservice.shared.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.shared.annotation.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonRegistrationRequest {
    private UUID id;
    @NotNull
    @ValidPassword
    @NotBlank(message = "Password is mandatory!")
    private String password;
    @NotNull
    @ValidEmail
    @UniqueEmail
    @NotBlank(message = "Email is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters!")
    private String email;
    @NotNull
    @ValidPhoneNumber
    @UniquePhoneNumber
    @NotBlank(message = "Phone number is mandatory!")
    @Size(min = 13, max = 13, message = "Must be 10 characters!")
    private String phoneNumber;
}
