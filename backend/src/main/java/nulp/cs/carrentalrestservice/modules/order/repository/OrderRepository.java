package nulp.cs.carrentalrestservice.modules.order.repository;

import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
import nulp.cs.carrentalrestservice.modules.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository <CarOrder, UUID> {
    //TODO
    @Query("SELECT COUNT(o) > 0 FROM CarOrder o WHERE o.person.id = :personId " +
            "AND (o.schedule.startDate <= :endDate AND o.schedule.endDate >= :startDate)")
    boolean isCustomerHasOverlapOrder (@Param("personId") UUID personId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<CarOrder> findAllByPerson_IdAndStatus(UUID personId, OrderStatus status);
}
