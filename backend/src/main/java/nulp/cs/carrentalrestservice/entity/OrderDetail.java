package nulp.cs.carrentalrestservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "ordersDetails")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private  long id;
    @Column(nullable = false)
    private int numberOfDays;
    @Column(nullable = false)
    //separate into another entity
    private LocalDate pickUpDate;
    @Column(nullable = false)
    private LocalDate dropOffDate;
    @Column(nullable = false)
    private String pickUpLocation;
    @Column(nullable = false)
    private String dropOffLocation;
    @Column(nullable = false)
    private double totalPrice;
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "carId", nullable = false)
    private Car car;
    @Column(columnDefinition = "varchar(50)")
    private String comment;

    @OneToOne(mappedBy = "orderDetail")
    private CarOrder carOrder;



    public void setCar(Car car) {
        this.car = car;
        car.getOrderDetails().add(this);
    }


}
