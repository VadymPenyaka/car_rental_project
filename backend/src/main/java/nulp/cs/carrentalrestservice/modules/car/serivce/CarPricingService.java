package nulp.cs.carrentalrestservice.modules.car.serivce;

import nulp.cs.carrentalrestservice.modules.car.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;

import java.util.Optional;
import java.util.UUID;

public interface CarPricingService {
    Optional<CarPricingDTO> getCarPricingById (UUID id);

    Optional<CarPricingDTO> updateCarPricingByID (UUID id, CarPricingDTO carPricing);

    Boolean deleteCarPricingById (UUID id);

    void createCarPricing (CarPricingDTO carPricingDTO);

    Optional<CarPricingDTO> getCarPricingByCarId (UUID carId);

    Double calculateOrderPrice(OrderCreationRequest orderRequest);
}
