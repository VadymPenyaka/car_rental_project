package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPricing {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
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
