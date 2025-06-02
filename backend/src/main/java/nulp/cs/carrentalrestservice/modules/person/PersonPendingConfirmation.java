package nulp.cs.carrentalrestservice.modules.person;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.security.VerificationType;
import nulp.cs.carrentalrestservice.modules.security.SensitiveDataConverter;
import nulp.cs.carrentalrestservice.modules.person.mapper.PersonDTOConverter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonPendingConfirmation {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationType type;

    @Column(nullable = false, columnDefinition = "jsonb")
    @Convert(converter = PersonDTOConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    private PersonDTO data;

    @Column
    @Convert(converter = SensitiveDataConverter.class)
    private String username;

    @Column(nullable = false)
    private LocalDateTime expiresAt;
}