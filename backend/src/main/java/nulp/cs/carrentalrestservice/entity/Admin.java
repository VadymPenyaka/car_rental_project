package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "admins")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Comparable<Admin>{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 50)
    private String password;
    @OneToMany(mappedBy = "admin", cascade =  CascadeType.ALL)
    private Set<CarOrder> carOrders=new HashSet<>();

    @Override
    public int compareTo(Admin otherAdmin) {
        return Integer.compare(getCarOrders().size(), otherAdmin.getCarOrders().size());
    }
}
