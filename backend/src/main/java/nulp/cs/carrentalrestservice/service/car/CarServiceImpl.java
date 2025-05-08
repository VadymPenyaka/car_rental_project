package nulp.cs.carrentalrestservice.service.car;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.event.SaveCarPicturesEvent;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
import nulp.cs.carrentalrestservice.model.response.CategoryPriceRangeResponse;
import nulp.cs.carrentalrestservice.repository.CarJdbcRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final ApplicationEventPublisher eventPublisher;
    private final CarRepository carRepository;
    private final ModelService modelService;
    private final CarMapper carMapper;
    private final LoggingService loggingService;
    private final CarJdbcRepository carJdbcRepository;

    @Override
    @Transactional
    public void createCar(CarDTO carDTO, MultipartFile[] files) {
        carDTO.setModel(modelService.createIfNotExist(carDTO.getModel()));

        Car savedCar = carRepository
                .save(carMapper.carDtoToCar(carDTO));

        eventPublisher.publishEvent(new SaveCarPicturesEvent(this, savedCar.getId(), files));

        carMapper.carToCarDto(savedCar);
    }

    @Override
    @Cacheable(value = "categoryPricing", key = "'all'")
    public List<CategoryPriceRangeResponse> getCarCategoriesPriceRanges() {
        long start = System.currentTimeMillis();
        List<CategoryPriceRangeResponse> prices = carJdbcRepository.getCategoriesPriceRange();
        System.out.println(System.currentTimeMillis()-start);
        return prices;

    }

    @Override
    public List<CarCardResponse> getAllCarsByCriteria(CarSearchRequest carSearchRequest) {
        return carJdbcRepository.getAllCarsByCriteria(carSearchRequest);
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

    @Override
    public boolean isVinUsed(String vin) {
        return carRepository.existsByVin(vin);
    }
}
