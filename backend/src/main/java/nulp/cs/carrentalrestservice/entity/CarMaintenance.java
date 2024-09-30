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
    @ManyToOne
    private Car car;
    @OneToOne
    @JoinColumn(nullable = false, name = "schedule_id", referencedColumnName = "id")
    private CarSchedule schedule;
}
