package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import nulp.cs.carrentalrestservice.validation.ValidEmail;
import nulp.cs.carrentalrestservice.validation.ValidPassword;

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
    @NotBlank(message = "This field is mandatory!")
    @Column(nullable = false, length = 50)
    private String firstName;
    @NotBlank(message = "This field is mandatory!")
    @Column(nullable = false, length = 50)
    private String lastName;
    @NotBlank(message = "This field is mandatory!")
    @ValidEmail
    @Column(nullable = false, length = 50)
    private String email;
    @ValidPassword
    @NotBlank(message = "This field is mandatory")
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false, length = 12, name = "phone_number")
    @NotBlank(message = "This field is mandatory")
    private String phoneNumber;
    @OneToMany(mappedBy = "admin", cascade =  CascadeType.ALL)
    private Set<CarOrder> carOrders=new HashSet<>();

    @Override
    public int compareTo(Admin otherAdmin) {
        return Integer.compare(getCarOrders().size(), otherAdmin.getCarOrders().size());
    }
}
