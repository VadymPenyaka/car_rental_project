package nulp.cs.carrentalrestservice.repository;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.FuelType;
import nulp.cs.carrentalrestservice.model.GearboxType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;

    private Car car;


    @BeforeEach
    void setUp() {
        car = Car.builder()
                .id(Long.valueOf(1))
                .carClass(CarClass.BUSINESS)
                .brand("BMW")
                .isAvailable(true)
                .pricePerDay(100.0)
                .fuelConsumption(10)
                .location("Lviv")
                .gearboxType(GearboxType.AUTOMATIC)
                .fuelType(FuelType.DIESEL)
                .fuelConsumption(10)
                .numberOfSeats(5)
                .model("X5")
                .build();
    }

    @Test
    @Rollback
    @Transactional
    public void createCarTest_ReturnNotNull () {
        Car savedCar = carRepository.save(car);

        assertThat(savedCar).isNotNull();
    }

    @Test
    @Rollback
    @Transactional
    public void getCarTest_ReturnCar() {
        Car savedCar = carRepository.save(car);
        Car foundCar = carRepository
                .findById(savedCar.getId()).get();

        assertThat(foundCar.getBrand()).isEqualTo(foundCar.getBrand());
    }
}
