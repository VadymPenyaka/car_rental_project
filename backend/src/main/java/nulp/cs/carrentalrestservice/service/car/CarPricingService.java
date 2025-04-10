package nulp.cs.carrentalrestservice.service.car;

import nulp.cs.carrentalrestservice.model.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.util.Optional;
import java.util.UUID;

public interface CarPricingService {
    Optional<CarPricingDTO> getCarPricingById (UUID id);

    Optional<CarPricingDTO> updateCarPricingByID (UUID id, CarPricingDTO carPricing);

    Boolean deleteCarPricingById (UUID id);

    CarPricingDTO createCarPricing (CarPricingDTO carPricingDTO);

    Optional<CarPricingDTO> getCarPricingByCarId (UUID carId);

    Double calculateOrderPrice(OrderCreationRequest orderRequest);
}
