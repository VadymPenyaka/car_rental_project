package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.CarPricing;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.mapper.CarPricingMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import nulp.cs.carrentalrestservice.repository.CarPricingRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CarPricingControllerTest {
    @Autowired
    private CarPricingRepository carPricingRepository;
    @Autowired
    private CarPricingMapper mapper;
    @Autowired
    private CarPricingController controller;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarMapper carMapper;


    @Test
    void getCarPricingById() {
        CarPricingDTO expected = mapper
                .carPricingToCarPricingDto(carPricingRepository.findAll().get(0));

        CarPricingDTO actual = controller.getCarPricingById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Transactional
    @Rollback
    void updateCarPricingById() {
        CarPricingDTO pricingToUpdate = mapper
                .carPricingToCarPricingDto(carPricingRepository.findAll().get(0));

        pricingToUpdate.setPledge(1.0);
        ResponseEntity responseEntity = controller
                .updateCarPricingById(pricingToUpdate.getId(), pricingToUpdate);
        CarPricingDTO updated = mapper
                .carPricingToCarPricingDto(carPricingRepository.findAll().get(0));

        assertThat(responseEntity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(pricingToUpdate).isEqualTo(updated);
    }

    @Test
    @Rollback
    @Transactional
    void deleteCarPricingById() {
        CarPricingDTO pricingToDelete = mapper
                .carPricingToCarPricingDto(carPricingRepository.findAll().get(0));

        ResponseEntity response = controller.deleteCarPricingById(pricingToDelete.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getCarPricingByCarId() {
        CarPricingDTO expected = mapper
                .carPricingToCarPricingDto(carPricingRepository.findAll().get(0));
        CarDTO car = carMapper.carToCarDto(carRepository.findAll().get(0));

        CarPricingDTO actual = controller.getCarPricingByCarId(car.getId());

        assertThat(actual).isEqualTo(expected);
    }
}