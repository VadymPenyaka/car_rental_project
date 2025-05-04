package nulp.cs.carrentalrestservice.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.*;
import nulp.cs.carrentalrestservice.model.enumeration.Role;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private UUID id;
    private Role role;
    @NotNull
    @ValidEmail
    @UniqueEmail
    @NotBlank(message = "Email is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters!")
    private String username;
    @NotNull
    @ValidPassword
    @NotBlank(message = "Password is mandatory!")
    private String password;
    @NotBlank(message = "Sure name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String sureName;
    @NotBlank(message = "Name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String firstName;
    @NotNull
    @ValidPhoneNumber
    @UniquePhoneNumber
    @NotBlank(message = "Phone number is mandatory!")
    @Size(min = 13, max = 13, message = "Must be  characters!")
    private String phoneNumber;
}
