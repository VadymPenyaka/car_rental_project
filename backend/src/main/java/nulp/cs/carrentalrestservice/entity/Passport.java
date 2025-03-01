package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passports")
public class Passport {
    @Id
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @GeneratedValue
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String documentNumber;

    @Column(nullable = false)
    private String issuedBy;

    @Column(nullable = false)
    private LocalDate expirationDate;

    private String taxIdentificationNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "document_id")
    private Document document;

}
