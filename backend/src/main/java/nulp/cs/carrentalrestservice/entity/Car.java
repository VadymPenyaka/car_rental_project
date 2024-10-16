package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.enumeration.DriveType;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "cars")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(nullable = false, columnDefinition = "varchar(50)", length = 50)
    private String brand;
    @Column(nullable = false, length = 50)
    private String model;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarClass carClass;
    @Column(nullable = false)
    private Integer fuelConsumption;
    @Column(nullable = false)
    private Integer numberOfSeats;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GearboxType gearboxType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriveType driveType;
    @Column(nullable = false)
    private Double engineCapacity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_pricing_id")
    private CarPricing carPricing;
    @OneToMany(mappedBy = "car")
    private Set<CarSchedule> carSchedules;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Location location;

}
