package nulp.cs.carrentalrestservice.modules.order.enity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.car.CarSchedule;
import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import nulp.cs.carrentalrestservice.modules.payment.Payment;
import nulp.cs.carrentalrestservice.modules.person.entity.Person;
import nulp.cs.carrentalrestservice.modules.admin.enity.Admin;
import nulp.cs.carrentalrestservice.modules.document.enity.Document;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
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
    private Person person;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    @Column(nullable = false)
    private long totalPrice;
    @OneToMany(mappedBy = "order")
    private Set<Document> documents = new HashSet<>();
    @OneToMany(mappedBy = "order")
    private Set<Payment> payments;

    @Column(columnDefinition = "varchar(100)")
    private String comment;
    @OneToOne
    @JoinColumn(nullable = false)
    private CarSchedule schedule;
}

