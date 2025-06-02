package nulp.cs.carrentalrestservice.modules.order.service;

import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    void createCarOrder (OrderCreationRequest request);

    Optional<CarOrderDTO> getCarOrderByID (UUID id);

    Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO);

    boolean isOwner (UUID orderId, String username);

    boolean isCustomerHasOverlapOrder(UUID customerId, LocalDate startDate, LocalDate endDate);


}
