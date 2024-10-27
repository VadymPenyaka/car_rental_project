package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "cars_maintenance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenance {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @OneToOne
    @JoinColumn(nullable = false, name = "schedule_id")
    private CarSchedule schedule;
}
