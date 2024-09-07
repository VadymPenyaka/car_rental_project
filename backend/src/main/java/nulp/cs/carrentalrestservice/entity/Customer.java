package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "customers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String firstName;
    @Column(nullable = false, length = 50)
    @NotBlank(message = "Sure name is mandatory!")
    @Size(min = 3, max = 50, message = "Must be between 3 and 50!")
    private String sureName;
    @Column(nullable = false, length = 9)
    @NotBlank(message = "Password is mandatory!")
    @Size(min = 8, max = 18, message = "Must be between 3 and 20!")
    private String passportId;
    @Column(nullable = false)
//    @NotBlank(message = "Birth date is mandatory!")
    private LocalDate birthDate;
    @Column(nullable = false, name = "passport_expiry_date")
//    @NotBlank(message = "Passport expiry date is mandatory!")
    private LocalDate passportExpiryDate;
    @Column(nullable = false)
    @NotBlank(message = "Email is mandatory!")
    @Size(min = 3, max = 50, message = "Email is incorrect!")
    private String email;
    @Column(nullable = false, name = "phone_number", length = 12)
    @NotBlank(message = "Phone number is mandatory!")
    @Size(min = 9, max = 11, message = "Must be 10 characters!")
    private String phoneNumber;


    @OneToMany(mappedBy = "customer")
    private Set<CarOrder> carOrders = new HashSet<>();
}
