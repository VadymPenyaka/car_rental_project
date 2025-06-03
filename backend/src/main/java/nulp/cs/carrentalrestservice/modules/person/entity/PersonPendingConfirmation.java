package nulp.cs.carrentalrestservice.modules.person.entity;

import jakarta.persistence.*;
import lombok.*;
import nulp.cs.carrentalrestservice.modules.person.dto.VerificationType;
import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;
import nulp.cs.carrentalrestservice.shared.converter.SensitiveDataConverter;
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