package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarPricingDTO;

import java.util.Optional;

public interface CarPricingService {
    Optional<CarPricingDTO> getCarPricingById (Long id);

    Optional<CarPricingDTO> updateCarPricingByID (Long id, CarPricingDTO carPricing);

    Boolean deleteCarPricingById (Long id);

    CarPricingDTO createCarPricing (CarPricingDTO carPricingDTO);

    Optional<CarPricingDTO> getCarPricingByCarId (Long carId);
}
