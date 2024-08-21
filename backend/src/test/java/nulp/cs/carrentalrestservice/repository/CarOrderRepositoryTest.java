package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.bootstrap.Bootstrap;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(Bootstrap.class)
public class CarOrderRepositoryTest {
    @Autowired
    private CarOrderRepository carOrderRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void saveCarOrderTest() {
        Admin admin = adminRepository.findAll().get(0);
        Customer customer = customerRepository.findAll().get(0);

        CarOrder savedCarOrder = carOrderRepository.save(
                CarOrder.builder()
                        .status(OrderStatus.IN_USE)
                        .admin(admin)
                        .customer(customer)
                        .build()
        );

        assertThat(savedCarOrder).isNotNull();
    }
}
