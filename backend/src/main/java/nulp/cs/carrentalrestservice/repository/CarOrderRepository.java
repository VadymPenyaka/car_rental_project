package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarOrderRepository extends JpaRepository<CarOrder, Long> {
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM car_orders o " +
            "WHERE o.car.id = :carId AND " +
            "((o.startDate <= :endDate AND o.endDate >= :startDate))")
    boolean existsByCarIdAndDatesOverlap(
            @Param("carId") Long carId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    List<CarOrder> getAllByStatus(OrderStatus orderStatus);

    List<CarOrder> getCarOrdersByAdminAndStatus (Admin admin, OrderStatus orderStatus);
}
