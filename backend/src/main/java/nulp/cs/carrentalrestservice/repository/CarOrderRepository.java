package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarOrderRepository extends JpaRepository<CarOrder, Long> {
    List<CarOrder> getAllByStatus(OrderStatus orderStatus);

    List<CarOrder> getCarOrdersByAdminAndStatus (Admin admin, OrderStatus orderStatus);
}
