package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final LoggingService loggingService;


    @Override
    public CarDTO createCar(CarDTO carDTO) {
        return carMapper.carToCarDto(carRepository
                .save(carMapper.carDtoToCar(carDTO)));

    }

    @Override
    public List<CarDTO> getAllCarsByCriteria(UUID carId,
                                             UUID locationId,
                                             CarClass carClass,
                                             String brand,
                                             GearboxType gearboxType,
                                             FuelType fuelType,
                                             LocalDate startDate,
                                             LocalDate endDate) {

        loggingService.logInfo("Getting cars by criteria");
        if(carId == null && locationId == null && carClass==null && brand == null && gearboxType == null && fuelType == null && startDate == null && endDate == null) {
            return carRepository.findAll().stream().map(carMapper::carToCarDto).toList();
        }

        return carRepository.findAllCarsByCriteria(carId, locationId, carClass, brand, gearboxType, fuelType, startDate, endDate).stream()
                .map(carMapper::carToCarDto).toList();
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

}
