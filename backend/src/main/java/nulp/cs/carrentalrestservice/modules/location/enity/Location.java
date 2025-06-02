package nulp.cs.carrentalrestservice.modules.location.enity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.admin.enity.Admin;
import nulp.cs.carrentalrestservice.modules.car.enity.Car;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(nullable = false, length = 50)
    private String locationName;
    @Column(nullable = false, length = 50)
    private String region;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false, length = 50)
    private String latitude;
    @Column(nullable = false, length = 50)
    private String longitude;
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<Car> cars;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private Set<Admin> admins;
}
