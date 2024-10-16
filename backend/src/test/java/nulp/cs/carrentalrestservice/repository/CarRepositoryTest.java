package nulp.cs.carrentalrestservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    @Test
    public void getCarTest_ReturnCar() {
        int size = carRepository.findAll().size();

        assertThat(size).isEqualTo(2);
    }
}
