package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "admins")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Comparable<Admin>{
    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(updatable = false, nullable = false, unique = true, columnDefinition = "VARCHAR(36)")
    private UUID id;
    @OneToMany(mappedBy = "admin", cascade =  CascadeType.ALL)
    private Set<CarOrder> carOrders = new HashSet<>();
    @OneToOne
    @JoinColumn
    private Person person;

    @Override
    public int compareTo(Admin otherAdmin) {
        return Integer.compare(getCarOrders().size(), otherAdmin.getCarOrders().size());
    }
}
