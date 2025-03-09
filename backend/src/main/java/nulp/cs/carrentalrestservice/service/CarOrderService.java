package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.security.PersonDetails;

import java.util.Optional;
import java.util.UUID;

public interface CarOrderService {
    CarOrderDTO createCarOrder (OrderCreationRequest request, PersonDetails personDetails);

    Optional<CarOrderDTO> getCarOrderByID (UUID id);

    Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO);

    boolean isOwner (UUID orderId, String username);

}
