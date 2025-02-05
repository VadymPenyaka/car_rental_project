package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.UniqueEmail;
import nulp.cs.carrentalrestservice.annotation.UniquePassportId;
import nulp.cs.carrentalrestservice.annotation.UniquePhoneNumber;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private UUID id;
    private String firstName;
    private String sureName;
    @UniquePassportId
    private String passportId;
    private LocalDate birthDate;
    private LocalDate passportExpiryDate;
    @UniqueEmail
    private String email;
    private String password;
    @UniquePhoneNumber
    private String phoneNumber;
}
