package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.entity.CarPricing;
import nulp.cs.carrentalrestservice.mapper.CarPricingMapper;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import nulp.cs.carrentalrestservice.model.FuelType;
import nulp.cs.carrentalrestservice.model.GearboxType;
import nulp.cs.carrentalrestservice.repository.CarPricingRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarPricingServiceImplTest {
    @Mock
    private CarPricingRepository carPricingRepository;
    @Mock
    private CarPricingMapper carPricingMapper;
    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarPricingServiceImpl carPricingService;

    private CarPricing carPricing;
    private CarPricingDTO carPricingDTO;
    private Car car;

    @BeforeEach
    void setUp() {


        carPricingDTO = CarPricingDTO.builder()
                .pledge(300.0)
                .upToThreeDays(250.0)
                .upToTenDays(200.0)
                .upToMonth(150.0)
                .moreThenMonth(120.0)
                .build();

        carPricing = CarPricing.builder()
                .pledge(300.0)
                .upToThreeDays(250.0)
                .upToTenDays(200.0)
                .upToMonth(150.0)
                .moreThenMonth(120.0)
                .car(car)
                .build();

        car = Car.builder()
                .carClass(CarClass.BUSINESS)
                .brand("BMW")
                .model("X5")
                .carPricing(carPricing)
                .fuelConsumption(10)
                .numberOfSeats(5)
                .fuelType(FuelType.DIESEL)
                .gearboxType(GearboxType.AUTO)
                .build();
    }

    @Test
    void getCarPricingById() {
        when(carPricingRepository.findById(any())).thenReturn(Optional.ofNullable(carPricing));
        when(carPricingMapper.carPricingToCarPricingDto(any())).thenReturn(carPricingDTO);

        CarPricingDTO actual = carPricingService
                .getCarPricingById(carPricing.getId()).get();

        assertThat(actual).isNotNull();
    }
    @Test
    void updateCarPricingByID() {
        when(carPricingRepository.findById(any()))
                .thenReturn(Optional.ofNullable(carPricing));
        carPricingDTO.setPledge(0.0);
        when(carPricingRepository.save(any()))
                .thenReturn(carPricing);
        when(carPricingMapper.carPricingToCarPricingDto(any()))
                .thenReturn(carPricingDTO);


        CarPricingDTO actual = carPricingService
                .updateCarPricingByID(carPricing.getId(), carPricingDTO).get();

        assertThat(actual).isNotNull();
        assertThat(actual.getPledge()).isEqualTo(0.0);
    }


    @Test
    void deleteCarPricingById_true() {
        when(carPricingRepository.existsById(any())).thenReturn(true);

        boolean isDelete = carPricingService.deleteCarPricingById(carPricing.getId());

        assertThat(isDelete).isTrue();
    }

    @Test
    void deleteCarPricingById_false() {
        when(carPricingRepository.existsById(any())).thenReturn(false);

        boolean isDelete = carPricingService.deleteCarPricingById(carPricing.getId());

        assertThat(isDelete).isFalse();
    }

    @Test
    void getCarPricingByCarId() {
        when(carRepository.findById(any()))
                .thenReturn(Optional.ofNullable(car));
        when(carPricingMapper.carPricingToCarPricingDto(carPricing))
                .thenReturn(carPricingDTO);

        CarPricingDTO actual = carPricingService.getCarPricingByCarId(car.getId()).get();

        assertThat(actual).isNotNull();
    }

}