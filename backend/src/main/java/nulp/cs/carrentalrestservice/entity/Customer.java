package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.entity.bankid.DriverLicense;
import nulp.cs.carrentalrestservice.entity.bankid.Passport;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "passport_id", referencedColumnName = "id")
    private Passport passport;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private DriverLicense driverLicense;

    @OneToMany(mappedBy = "customer")
    private Set<CarOrder> carOrders = new HashSet<>();
    @OneToOne
    @JoinColumn
    private Person person;
}
