package nulp.cs.carrentalrestservice.service.car;

import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequestDto;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarService {
    CarDTO createCar (CarDTO carDTO);

    List<CarCardResponse> getAllCarsByCriteria(CarSearchRequestDto carDto);

    Boolean deleteCarById (UUID id);

    Optional<CarDTO> updateCarByID(UUID id, CarDTO carDTO);

    Optional<CarCustomerDetailsResponse> getCarCustomerDetailsById(UUID id);

    Optional<CarDTO> getCarFullDetailsById(UUID id);

    boolean verifyCarForOrder (OrderCreationRequest orderCreationRequest);
}
