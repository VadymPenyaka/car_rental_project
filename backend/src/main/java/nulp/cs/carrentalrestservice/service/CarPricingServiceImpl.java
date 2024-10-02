package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.mapper.CarPricingMapper;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import nulp.cs.carrentalrestservice.repository.CarPricingRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarPricingServiceImpl implements CarPricingService {
    private final CarPricingRepository carPricingRepository;
    private final CarRepository carRepository;

    private final CarPricingMapper carPricingMapper;

    @Override
    public Optional<CarPricingDTO> getCarPricingById(Long id) {
        return Optional.ofNullable(carPricingMapper
                .carPricingToCarPricingDto(carPricingRepository.findById(id).get()));
    }

    @Override
    public Optional<CarPricingDTO> updateCarPricingByID(Long id, CarPricingDTO carPricing) {
        AtomicReference<Optional<CarPricingDTO>> atomicReference = new AtomicReference<>();

        carPricingRepository.findById(id).ifPresentOrElse( foundPricing -> {
            foundPricing.setPledge(carPricing.getPledge());
            foundPricing.setUpToThreeDays(carPricing.getUpToThreeDays());
            foundPricing.setUpToTenDays(carPricing.getUpToTenDays());
            foundPricing.setUpToMonth(carPricing.getUpToMonth());
            foundPricing.setMoreThenMonth(carPricing.getMoreThenMonth());

            atomicReference.set(Optional.of(carPricingMapper
                    .carPricingToCarPricingDto(carPricingRepository.save(foundPricing))));
        }, ()-> {
                    atomicReference.set(Optional.empty());
                }
        );

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCarPricingById(Long id) {
        if(carPricingRepository.existsById(id)) {
            carPricingRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public CarPricingDTO createCarPricing(CarPricingDTO carPricingDTO) {
        return carPricingMapper.carPricingToCarPricingDto(carPricingRepository
                .save(carPricingMapper.carPricingDtoToCarPricing(carPricingDTO)));
    }

    @Override
    public Optional<CarPricingDTO> getCarPricingByCarId(Long carId) {
        Car car = carRepository.findById(carId).get();
        CarPricingDTO carPricingDTO = carPricingMapper.carPricingToCarPricingDto(car.getCarPricing());
        return Optional.ofNullable(carPricingDTO);
    }
}
