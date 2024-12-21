package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import nulp.cs.carrentalrestservice.annotation.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "customers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String firstName;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Sure name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String sureName;
    @Column(nullable = false, length = 9)
    @NotBlank(message = "Password ID is mandatory!")
    @Size(min = 9, max = 9, message = "Must be 9 digit length!")
    private String passportId;
    @Column(nullable = false)
    @ValidBirthDate
    @NotNull(message = "Birth date is mandatory!")
    private LocalDate birthDate;
    @ValidExpiryDate
    @Column(nullable = false, name = "passport_expiry_date")
    @NotNull(message = "Passport expiry date is mandatory!")
    private LocalDate passportExpiryDate;
    @ValidEmail
    @Column(nullable = false)
    @NotBlank(message = "Email is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50 characters!")
    private String email;
//    @ValidPassword
    @NotBlank(message = "This field is mandatory")
    @Column(nullable = false, length = 100)
    private String password;
    @ValidPhoneNumber
    @Column(nullable = false, name = "phone_number", length = 12)
    @NotBlank(message = "Phone number is mandatory!")
    @Size(min = 9, max = 11, message = "Must be 10 characters!")
    private String phoneNumber;


    @OneToMany(mappedBy = "customer")
    private Set<CarOrder> carOrders = new HashSet<>();
}
