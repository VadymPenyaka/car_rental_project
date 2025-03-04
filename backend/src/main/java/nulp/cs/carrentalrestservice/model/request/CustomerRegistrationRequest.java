package nulp.cs.carrentalrestservice.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationRequest {
    private UUID id;
    @ValidPassword
    @NotBlank(message = "Password is mandatory!")
    private String password;
    @NotBlank(message = "Sure name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String sureName;
    @NotBlank(message = "Name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String firstName;
    @ValidEmail
    @UniqueEmail
    @NotBlank(message = "Email is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters!")
    private String email;
    @ValidPhoneNumber
    @UniquePhoneNumber
    @NotBlank(message = "Phone number is mandatory!")
    @Size(min = 9, max = 11, message = "Must be 10 characters!")
    private String phoneNumber;
}
