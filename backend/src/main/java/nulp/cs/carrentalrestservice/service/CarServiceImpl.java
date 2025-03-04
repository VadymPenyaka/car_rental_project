package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.entity.CarPricing;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequestDto;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
import nulp.cs.carrentalrestservice.repository.CarPricingRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarPricingRepository carPricingRepository;
    private final CarMapper carMapper;
    private final LoggingService loggingService;


    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Car car = carMapper.carDtoToCar(carDTO);
        CarPricing carPricing = car.getCarPricing();
        car.setCarPricing(carPricingRepository.save(carPricing));

        return carMapper.carToCarDto(carRepository
                .save(car));

    }

    @Override
    public List<CarCardResponse> getAllCarsByCriteria(CarSearchRequestDto carDTO) {

        loggingService.logInfo("Getting cars by criteria");
        if(carDTO.getId() == null && carDTO.getLocation() == null && carDTO.getCarClass()==null && carDTO.getBrand() == null && carDTO.getGearboxType() == null && carDTO.getFuelType() == null && carDTO.getStartDate() == null && carDTO.getEndDate() == null) {
            return carRepository.findAll().stream().map(carMapper::carToCarCardDto).toList();
        }

        UUID locationId = carDTO.getLocation() != null ? carDTO.getLocation().getId() : null;
        return carRepository.findAllCarsByCriteria(carDTO.getId(), locationId, carDTO.getCarClass(), carDTO.getBrand(), carDTO.getGearboxType(), carDTO.getFuelType(), carDTO.getStartDate(), carDTO.getEndDate()).stream()
                .map(carMapper::carToCarCardDto).toList();
    }

    @Override
    public Boolean deleteCarById(UUID id) {
        loggingService.logInfo("Deleting car for ID: " + id);
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            loggingService.logInfo("Car deleted successfully");
            return true;
        }
        loggingService.logInfo("Car not found for ID: "+id);
        return false;
    }

    @Override
    public Optional<CarDTO> updateCarByID(UUID id, CarDTO carDTO) {
        loggingService.logInfo("Updating car for ID: " + id);
        AtomicReference<Optional<CarDTO>> atomicReference = new AtomicReference<>();

        carRepository.findById(id).ifPresentOrElse( foundCar -> {
                foundCar.setBrand(carDTO.getBrand());
                foundCar.setModel(carDTO.getModel());
                foundCar.setCarClass(carDTO.getCarClass());
                foundCar.setFuelType(carDTO.getFuelType());
                atomicReference.set(Optional.of(carMapper
                        .carToCarDto(carRepository.save(foundCar))));
                loggingService.logInfo("Car updated successfully");
            }, ()-> {
                loggingService.logInfo("Car not found for ID: "+id);
                atomicReference.set(Optional.empty());
            }
        );

        return atomicReference.get();
    }

    @Override
    public Optional<CarCustomerDetailsResponse> getCarCustomerDetailsById(UUID id) {
        return Optional.ofNullable(carMapper
                .carToCustomerDetailDto(carRepository
                        .findById(id).orElse(null)));
    }

    @Override
    public Optional<CarDTO> getCarFullDetailsById(UUID id) {
        return Optional.ofNullable(carMapper.carToCarDto(carRepository
                .findById(id).orElse(null)));
    }

}
