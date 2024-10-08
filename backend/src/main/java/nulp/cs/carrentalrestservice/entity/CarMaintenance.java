package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cars_maintenance")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @OneToOne
    @JoinColumn(nullable = false, name = "schedule_id")
    private CarSchedule schedule;
}
