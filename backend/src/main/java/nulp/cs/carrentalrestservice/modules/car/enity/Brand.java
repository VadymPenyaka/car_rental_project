package nulp.cs.carrentalrestservice.modules.car.enity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String name;

    @OneToMany(mappedBy = "brandName")
    private Set<Model> models;
}
