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

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column
    private String passportId;
    @Column
    @NotNull(message = "Birth date is mandatory!")
    private LocalDate birthDate;
    @Column(name = "passport_expiry_date")
    @NotNull(message = "Passport expiry date is mandatory!")
    private LocalDate passportExpiryDate;

    @OneToMany(mappedBy = "customer")
    private Set<CarOrder> carOrders = new HashSet<>();
}
