package nulp.cs.carrentalrestservice.repository;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.entity.OrderDetail;
import nulp.cs.carrentalrestservice.model.CarClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Car car;
    private OrderDetail orderDetail;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .birthDate(LocalDate.now())
                .expiryDate(LocalDate.now())
                .firstName("FirstName")
                .lastName("LastName")
                .sureName("SureName")
                .passportId("12345678")
                .build();
        customerRepository.saveAndFlush(customer);
        car = carRepository.findAll().get(0);


        orderDetail = OrderDetail.builder()
                .pickUpDate(LocalDate.now())
                .pickUpLocation("Lviv")
                .dropOffLocation("Lviv")
                .dropOffDate(LocalDate.now())
                .numberOfDays(1)
                .totalPrice(220.0)
                .car(car)
                .customer(customer)
                .build();
    }

    @Test
    void saveOrderInfoTest() {
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);

        assertThat(savedOrderDetail).isNotNull();
    }
}
