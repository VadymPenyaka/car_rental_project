package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CarControllerIT {
    @Autowired
    private CarController controller;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarRepository carRepository;

    @Test
    void getAllCarsByCriteria() {
        List<CarDTO> carDTOS = controller.getAllCarsByCriteria(null, null, null, null, null, null, null);
        assertThat(carDTOS.size()).isEqualTo(2);
    }

    @Test
    @Rollback
    @Transactional
    void createCar() {
        CarDTO carDTO = carMapper
                .carToCarDto(carRepository
                        .findAll().get(0));

        ResponseEntity responseEntity = controller.createCar(carDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
    }

    @Test
    void getCarByID() {
        CarDTO expected = carMapper.carToCarDto(carRepository.findAll().get(0));

        CarDTO actual = controller.getCarByID(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Rollback
    @Transactional
    void updateCarById() {
        Car expected = carRepository.findAll().get(0);
        CarDTO expectedDTO = carMapper.carToCarDto(expected);
        final String updateModel = "updated";

        expectedDTO.setModel(updateModel);

        ResponseEntity responseEntity = controller.updateCarById(expected.getId(), expectedDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(carRepository.findAll().get(0).getModel()).isEqualTo(updateModel);
    }

    @Test
    @Rollback
    @Transactional
    void deleteCarById () {
        Car car = carRepository.findAll().get(0);

        ResponseEntity responseEntity = controller.deleteCarById(car.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}