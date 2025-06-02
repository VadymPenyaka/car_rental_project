package nulp.cs.carrentalrestservice.modules.car.enity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPricing {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(nullable = false)
    private Double pledge;
    @Column(nullable = false)
    private Double upToThreeDays;
    @Column(nullable = false)
    private Double upToTenDays;
    @Column(nullable = false)
    private Double upToMonth;
    @Column(nullable = false)
    private Double moreThenMonth;
    @OneToOne(mappedBy = "carPricing")
    private Car car;
}
