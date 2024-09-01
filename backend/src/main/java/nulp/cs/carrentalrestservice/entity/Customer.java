package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
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
    private String firstName;
    @Column(nullable = false, length = 50)
    private String sureName;
    @Column(nullable = false, length = 9)
    private String passportId;
    @Column(nullable = false)
    private LocalDate birthDate;
    @Column(nullable = false, name = "passport_expiry_date")
    private LocalDate passportExpiryDate;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, name = "phone_number", length = 12)
    private String phoneNumber;


    @OneToMany(mappedBy = "customer")
    private Set<CarOrder> carOrders = new HashSet<>();
}
