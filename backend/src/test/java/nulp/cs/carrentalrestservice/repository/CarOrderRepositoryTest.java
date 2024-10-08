package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CarOrderRepositoryTest {
    @Autowired
    private CarOrderRepository carOrderRepository;

    @Test
    void getCarOrderTest() {
        List<CarOrder> foundCarOrders = carOrderRepository.findAll();

        assertThat(foundCarOrders.size()).isEqualTo(1);
    }
}
