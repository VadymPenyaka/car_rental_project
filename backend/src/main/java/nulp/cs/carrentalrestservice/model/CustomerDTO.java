package nulp.cs.carrentalrestservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String sureName;
    private String passportId;
    private LocalDate birthDate;
    private LocalDate expiryDate;
}
