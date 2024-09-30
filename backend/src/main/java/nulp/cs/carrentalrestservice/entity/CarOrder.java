package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.OrderStatus;

import java.time.LocalDate;

@Entity(name = "car_orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrder {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Admin admin;
    @ManyToOne
    @JoinColumn( nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Car car;
    @Column(nullable = false)
    private double totalPrice;
    @Column(columnDefinition = "varchar(100)")
    private String comment;
    @OneToOne
    @JoinColumn(nullable = false, name = "schedule_id", referencedColumnName = "id")
    private CarSchedule schedule;

}

