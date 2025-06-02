package nulp.cs.carrentalrestservice.modules.car.enity;


import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.car.CarSchedule;
import nulp.cs.carrentalrestservice.modules.car.dto.*;
import nulp.cs.carrentalrestservice.modules.location.enity.Location;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(columnDefinition = "varchar(36)")
    private Model model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(36)")
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private CarClass carClass;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private GearboxType gearboxType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private DriveType driveType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_pricing_id")
    private CarPricing carPricing;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private CarDetails carDetails;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private CarRegistrationInfo registrationInfo;

    @OneToMany(mappedBy = "car")
    private Set<CarSchedule> carSchedules;

}
