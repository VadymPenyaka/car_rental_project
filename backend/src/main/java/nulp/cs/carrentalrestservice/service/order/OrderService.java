package nulp.cs.carrentalrestservice.service.order;

import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    CarOrderDTO createCarOrder (OrderCreationRequest request);

    Optional<CarOrderDTO> getCarOrderByID (UUID id);

    Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO);

    boolean isOwner (UUID orderId, String username);

    boolean isCustomerHasOverlapOrder(UUID customerId, LocalDate startDate, LocalDate endDate);


}
