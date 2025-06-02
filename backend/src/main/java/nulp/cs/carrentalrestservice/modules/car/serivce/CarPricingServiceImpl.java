package nulp.cs.carrentalrestservice.modules.car.serivce;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.enity.Car;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.modules.car.mapper.CarPricingMapper;
import nulp.cs.carrentalrestservice.modules.car.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.modules.car.repository.CarPricingRepository;
import nulp.cs.carrentalrestservice.modules.car.repository.CarRepository;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarPricingServiceImpl implements CarPricingService {
    private final CarPricingRepository carPricingRepository;
    private final CarRepository carRepository;
    private final LoggingService loggingService;
    private final CarPricingMapper carPricingMapper;

    @Override
    public Optional<CarPricingDTO> getCarPricingById(UUID id) {
        loggingService.logInfo("Getting car pricing for ID: " + id);
        return Optional.ofNullable(carPricingMapper
                .carPricingToCarPricingDto(carPricingRepository.findById(id).get()));
    }

    @Override
    public Optional<CarPricingDTO> updateCarPricingByID(UUID id, CarPricingDTO carPricing) {
        loggingService.logInfo("Updating car pricing for ID: " + id);
        AtomicReference<Optional<CarPricingDTO>> atomicReference = new AtomicReference<>();

        carPricingRepository.findById(id).ifPresentOrElse( foundPricing -> {
            foundPricing.setPledge(carPricing.getPledge());
            foundPricing.setUpToThreeDays(carPricing.getUpToThreeDays());
            foundPricing.setUpToTenDays(carPricing.getUpToTenDays());
            foundPricing.setUpToMonth(carPricing.getUpToMonth());
            foundPricing.setMoreThenMonth(carPricing.getMoreThenMonth());

            atomicReference.set(Optional.of(carPricingMapper
                    .carPricingToCarPricingDto(carPricingRepository.save(foundPricing))));

            loggingService.logInfo("Car pricing updated successfully");
        }, ()-> {
            atomicReference.set(Optional.empty());
            loggingService.logInfo("Car pricing not found for ID: "+id);
        }
        );

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCarPricingById(UUID id) {
        loggingService.logInfo("Deleting car pricing for ID: " + id);
        if(carPricingRepository.existsById(id)) {
            carPricingRepository.deleteById(id);
            loggingService.logInfo("Car pricing deleted successfully");
            return true;
        }
        loggingService.logInfo("Car pricing not found for ID: "+id);
        return false;
    }

    @Override
    public void createCarPricing(CarPricingDTO carPricingDTO) {
        loggingService.logInfo("Creating car pricing");
        carPricingMapper.carPricingToCarPricingDto(carPricingRepository
                .save(carPricingMapper.carPricingDtoToCarPricing(carPricingDTO)));
    }

    @Override
    public Optional<CarPricingDTO> getCarPricingByCarId(UUID carId) {
        loggingService.logInfo("Getting car pricing for car ID: " + carId);
        Car car = carRepository.findById(carId).orElseThrow(()-> new NotFoundException("Car with this id dont exists! "));
        CarPricingDTO carPricingDTO = carPricingMapper.carPricingToCarPricingDto(car.getCarPricing());
        if (carPricingDTO == null)
            loggingService.logInfo("Car pricing not found for car ID: " + carId);
        return Optional.ofNullable(carPricingDTO);
    }

    @Override
    public Double calculateOrderPrice(OrderCreationRequest orderRequest) {
        CarPricingDTO carPricingDTO = getCarPricingByCarId(orderRequest.getCarId())
                .orElseThrow(()-> new NotFoundException("Car pricing not found for this car"));

        long numberOfDays = ChronoUnit.DAYS.between(orderRequest.getStartDate(), orderRequest.getEndDate());
        double sum;
        if (numberOfDays<3) {
            sum = numberOfDays * carPricingDTO.getUpToThreeDays();
        } else if (numberOfDays<10) {
            sum = numberOfDays * carPricingDTO.getUpToTenDays();
        } else if (numberOfDays<30) {
            sum = numberOfDays * carPricingDTO.getUpToMonth();
        } else {
            sum = numberOfDays * carPricingDTO.getMoreThenMonth();
        }

        return sum + carPricingDTO.getPledge();
    }
}
