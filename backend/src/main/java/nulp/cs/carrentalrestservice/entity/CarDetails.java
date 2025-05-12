package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDetails {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(nullable = false)
    private Double engineCapacity;

    @Column(nullable = false)
    private int fuelTankCapacity;

    @Column(nullable = false)
    private int trunkCapacity;

    @Column(nullable = false)
    private Integer fuelConsumption;

    @Column(nullable = false)
    private Integer numberOfSeats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(5)")
    private LicenseCategory licenseCategory;

    @Column(nullable = false, columnDefinition = "int default 1")
    private int requiredExperience;

    @Column(nullable = false, columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> configuration;

    @OneToOne(mappedBy = "carDetails")
    private Car car;
}
