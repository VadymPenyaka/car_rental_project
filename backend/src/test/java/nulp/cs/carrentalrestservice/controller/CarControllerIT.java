package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarControllerIT {
    @Autowired
    private CarController controller;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private Flyway flyway;

    @Test
    void getAllCars() {
        List<CarDTO> carDTOS = controller.getAllCars();
        System.out.println(carDTOS);
        assertThat(carDTOS.size()).isEqualTo(1);
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