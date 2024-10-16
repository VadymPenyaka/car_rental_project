package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarPricingDTO;

import java.util.Optional;
import java.util.UUID;

public interface CarPricingService {
    Optional<CarPricingDTO> getCarPricingById (UUID id);

    Optional<CarPricingDTO> updateCarPricingByID (UUID id, CarPricingDTO carPricing);

    Boolean deleteCarPricingById (UUID id);

    CarPricingDTO createCarPricing (CarPricingDTO carPricingDTO);

    Optional<CarPricingDTO> getCarPricingByCarId (UUID carId);
}
