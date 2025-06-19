package nulp.cs.carrentalrestservice.modules.order.service;

import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import nulp.cs.carrentalrestservice.modules.payment.dto.StripeResponse;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    StripeResponse createCarOrder (OrderCreationRequest request);

    Optional<CarOrderDTO> getCarOrderByID (UUID id);

    //TODO ???
    Optional<CarOrderDTO> updateOrderStatusById(UUID id, OrderStatus orderStatus);

    boolean isOwner (UUID orderId, String username);

    boolean isCustomerHasOverlapOrder(UUID customerId, LocalDate startDate, LocalDate endDate);


    List<CarOrderDTO> getOrdersByPersonId (UUID personId, OrderStatus orderStatus);
}
