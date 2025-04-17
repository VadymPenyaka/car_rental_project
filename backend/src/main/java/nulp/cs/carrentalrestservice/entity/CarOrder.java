package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.OrderStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrder {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
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
    @OneToOne
    private Payment payment;

    @Column(columnDefinition = "varchar(100)")
    private String comment;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private CarSchedule schedule;
}

