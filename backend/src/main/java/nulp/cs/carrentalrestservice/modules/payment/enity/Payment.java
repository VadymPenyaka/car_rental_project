package nulp.cs.carrentalrestservice.modules.payment.enity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentMethod;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(nullable = false, unique = true)
    private String paymentIntentId;

    @Column
    private String cardBrand;

    @Column(length = 4)
    private String cardLastDigits;

    @Column
    private Integer cardExpMonth;

    @Column
    private Integer cardExpYear;

    @ManyToOne
    @JoinColumn(nullable = false)
    private CarOrder order;
}
