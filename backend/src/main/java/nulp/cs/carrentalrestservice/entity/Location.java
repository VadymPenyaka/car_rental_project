package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity(name = "locations")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(nullable = false, length = 50, name = "location_name")
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
    @OneToMany(mappedBy = "location")
    private Set<Car> cars;
}
