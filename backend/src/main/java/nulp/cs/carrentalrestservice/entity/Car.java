package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.FuelType;
import nulp.cs.carrentalrestservice.model.GearboxType;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "cars")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
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
    private String location;
    @Column(nullable = false)
    private FuelType fuelType;
    @Column(nullable = false, name = "gearbox_type")
    private GearboxType gearboxType;


    @OneToOne
    @JoinColumn(name = "carPricingId", referencedColumnName = "id")
    private CarPricing carPricing;
    @OneToMany(mappedBy = "car", cascade = CascadeType.REMOVE)
    private Set<CarOrder> carOrders;
}
