package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.OrderStatus;

import java.util.UUID;

@Entity
@Table(name = "car_orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrder {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Admin admin;
    @ManyToOne
    @JoinColumn( nullable = false)
    private Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private OrderStatus status;
    @Column(nullable = false)
    private double totalPrice;
    @Column(columnDefinition = "varchar(100)")
    private String comment;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(nullable = false, name = "schedule_id")
    private CarSchedule schedule;

}

