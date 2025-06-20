package nulp.cs.carrentalrestservice.modules.payment.enity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
import nulp.cs.carrentalrestservice.modules.payment.dto.PaymentStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
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

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(nullable = false, unique = true, length = 100, name = "payment_intent_id")
    private String paymentIntentId;

    @Column(unique = true, length = 100, name = "session_id")
    private String sessionId;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime paidAt;

    @Column(length = 20)
    private String cardBrand;

    @Column(length = 4)
    private String cardLastDigits;

    @Column(length = 500)
    private String receiptUrl;

    @ManyToOne
    @JoinColumn(nullable = false, name = "order_id")
    private CarOrder order;
}