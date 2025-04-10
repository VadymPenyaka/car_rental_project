package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Model {
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(columnDefinition = "varchar(50)", name = "brand_name")
    private Brand brandName;

    @Column
    private String modelName;

    @Column
    private int year;

    @Column
    private String description;

    @OneToMany(mappedBy = "model")
    private Set<Car> cars;
}
