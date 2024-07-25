package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderDetailDTO;

import java.util.Optional;

public interface OrderDetailService {
    OrderDetailDTO createOrderDetail (OrderDetailDTO orderDetailDTO);

    Optional<OrderDetailDTO> getOrderDetailById (Long id);

    Optional<OrderDetailDTO> updateOrderDetail (OrderDetailDTO orderDetailDTO, Long id);

    void deleteAllOrderDetailByCar(CarDTO car);

    boolean deleteOrderDetailById (Long id);

    Optional<OrderDetailDTO> getOrderDetailByOrder(CarOrderDTO carOrderDTO);
}
