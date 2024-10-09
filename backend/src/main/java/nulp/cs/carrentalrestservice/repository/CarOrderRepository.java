package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CarOrderRepository extends JpaRepository<CarOrder, UUID> {
    @Query("SELECT COUNT(o) > 0 FROM CarOrder o WHERE o.customer.id = :customerId " +
            "AND (o.schedule.startDate <= :endDate AND o.schedule.endDate >= :startDate)")
    boolean isCustomerHasOverlapOrder (@Param("customerId") Long customerId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
