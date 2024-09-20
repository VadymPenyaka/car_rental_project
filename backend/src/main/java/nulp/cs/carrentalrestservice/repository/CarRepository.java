package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.CarClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByCarClass (CarClass carClass);

    @Query("SELECT c FROM cars c WHERE NOT EXISTS (" +
            "SELECT o FROM car_orders o WHERE o.car = c AND (" +
            "o.startDate <= :endDate AND o.endDate >= :startDate))")
    List<Car> getAvailableCarsForPeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
