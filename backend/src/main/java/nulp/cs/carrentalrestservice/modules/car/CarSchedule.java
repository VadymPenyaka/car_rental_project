package nulp.cs.carrentalrestservice.modules.car;



import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.car.dto.ScheduleStatus;
import nulp.cs.carrentalrestservice.modules.car.enity.Car;
import nulp.cs.carrentalrestservice.modules.car.enity.CarMaintenance;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarSchedule {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @ManyToOne
    private Car car;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScheduleStatus status;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.ALL)
    private CarOrder carOrder;

    @OneToOne(mappedBy = "schedule")
    private CarMaintenance carMaintenance;
}



