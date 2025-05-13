package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.model.enumeration.BodyType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarRegistrationInfo {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Column(nullable = false, length = 36)
    private String vin;

    @Column(nullable = false, length = 36)
    private String number;

    @Column(nullable = false, length = 36)
    private String color;

    @Column(nullable = false, length = 36)
    private String passportId;

    @Column(nullable = false, length = 36)
    private String insurance_id;

    @OneToOne(mappedBy = "registrationInfo")
    private Car car;
}
