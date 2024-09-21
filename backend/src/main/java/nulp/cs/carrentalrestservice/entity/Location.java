package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "locations")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
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
