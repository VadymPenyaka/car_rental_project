package nulp.cs.carrentalrestservice.service;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.mapper.CarMapperImpl;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapperImpl carMapper;

    @Mock
    OrderDetailServiceImpl orderDetailService;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;
    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        car = Car.builder()
                .carClass(CarClass.BUSINESS)
                .brand("BMW")
                .isAvailable(true)
                .pricePerDay(100.0)
                .model("X5")
                .build();

        carDTO = CarDTO.builder()
                .carClass(CarClass.BUSINESS)
                .brand("BMW")
                .isAvailable(true)
                .pricePerDay(100.0)
                .model("X5")
                .build();
    }

    @Test
    void createCar() {
        when(carRepository.save(any())).thenReturn(car);
        when(carMapper.carToCarDto(any())).thenReturn(carDTO);

        CarDTO savedCar = carService.createCar(carDTO);

        assertThat(savedCar).isNotNull();
        assertThat(savedCar.getId()).isEqualTo(carDTO.getId());
    }

    @Test
    void getCarByID() {
        when(carRepository.findById(any())).thenReturn(Optional.ofNullable(car));
        when(carMapper.carToCarDto(any())).thenReturn(carDTO);

        CarDTO foundCar = carService.getCarByID(carDTO.getId()).get();

        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getId()).isEqualTo(carDTO.getId());
    }

    @Test
    void getAllCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(car));

        List<CarDTO> foundCars = carService.getAllCars();

        assertThat(foundCars.size()).isEqualTo(1);
    }

    @Test
    void deleteCarById_ReturnTrue() {
        when(carRepository.existsById(any())).thenReturn(true);
        when(carRepository.findById(any())).thenReturn(Optional.ofNullable(car));

        boolean isDeleted = carService.deleteCarById(carDTO.getId());

        verify(carRepository).deleteById(carDTO.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void deleteCarById_ReturnFalse() {
        when(carRepository.existsById(any())).thenReturn(false);

        boolean isDeleted = carService.deleteCarById(carDTO.getId());

        assertThat(isDeleted).isFalse();
    }

    @Test
    void updateCarByID() {
        Car expectedCar = Car.builder()
                .carClass(CarClass.BUSINESS)
                .brand("ToUpdate")
                .isAvailable(true)
                .pricePerDay(100.0)
                .model("X5")
                .build();

        when(carRepository.findById(any())).thenReturn(Optional.ofNullable(car));
        carDTO.setBrand(expectedCar.getBrand());
        when(carRepository.save(any())).thenReturn(expectedCar);
        when(carMapper.carToCarDto(any())).thenReturn(carDTO);

        CarDTO updatedCar  = carService.updateCarByID(carDTO.getId(), carDTO).get();

        assertThat(updatedCar.getId()).isEqualTo(carDTO.getId());
        assertThat(updatedCar.getBrand()).isEqualTo(expectedCar.getBrand());
    }
}