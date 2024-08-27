package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private long id;
    private String firstName;
    private String sureName;
    private String passportId;
    private LocalDate birthDate;
    private LocalDate passportExpiryDate;
    private String email;
}
