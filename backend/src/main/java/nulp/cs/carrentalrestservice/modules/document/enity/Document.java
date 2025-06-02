package nulp.cs.carrentalrestservice.modules.document.enity;


import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.document.dto.DocumentType;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Column(name = "document_number", insertable = false, updatable = false)
    private Integer documentNumber;

    @ManyToOne
    @JoinColumn()
    private CarOrder order;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
