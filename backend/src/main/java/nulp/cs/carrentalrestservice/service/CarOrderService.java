package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.util.Optional;
import java.util.UUID;

public interface CarOrderService {
    CarOrderDTO createCarOrder (CarOrderDTO carOrderDTO);

    Optional<CarOrderDTO> getCarOrderByID (UUID id);

    Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO);

    boolean isOwner (UUID orderId, String username);

    boolean isOrderValid (OrderCreationRequest creationRequest);

}
