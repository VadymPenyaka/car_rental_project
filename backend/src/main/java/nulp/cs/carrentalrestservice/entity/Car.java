package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.*;
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

    @Column(nullable = false, length = 36)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(36)")
    private BodyType bodyType;

    @Column(nullable = false, length = 36)
    private String number;

    @Column(nullable = false, length = 36)
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private CarClass carClass;

    @Column(nullable = false)
    private Integer fuelConsumption;

    @Column(nullable = false)
    private Integer numberOfSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private FuelType fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private GearboxType gearboxType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(50)")
    private DriveType driveType;

    @Column(nullable = false)
    private Double engineCapacity;

    @Column(nullable = false)
    private int fuelTankCapacity;

    @Column(nullable = false)
    private int trunkCapacity;

    @ManyToOne
    @JoinColumn(columnDefinition = "varchar(36)")
    private Model model;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(5)")
    private LicenseCategory licenseCategory;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int requiredExperience;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false)
    private Location location;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_pricing_id")
    private CarPricing carPricing;

    @OneToMany(mappedBy = "car")
    private Set<CarSchedule> carSchedules;



}
