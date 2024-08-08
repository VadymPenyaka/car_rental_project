package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface CarOrderService {
    CarOrderDTO createCarOrder (CarOrderDTO carOrderDTO);

    Optional<CarOrderDTO> getCarOrderByID (Long id);

    boolean deleteCarOrderById (Long id);

    Optional<CarOrderDTO> updateCarOrderById(Long id, CarOrderDTO carOrderDTO);

    List<CarOrderDTO> getAllCarOrdersByStatus(OrderStatus orderStatus);

    List<CarOrderDTO> getCarOrdersByAdminAndStatus (Long adminId, OrderStatus orderStatus);
}
