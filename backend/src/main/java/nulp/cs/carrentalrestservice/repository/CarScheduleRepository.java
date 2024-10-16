package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.UUID;

public interface CarScheduleRepository extends JpaRepository<CarSchedule, UUID> {
    @Query("SELECT COUNT(s) > 0 FROM CarSchedule s " +
            "WHERE s.car.id =:carId AND s.startDate <= :endDate " +
            "AND s.endDate >= :startDate AND (:scheduleId IS NULL OR s.id <> :scheduleId)")
    boolean isCarBooked (@Param("carId") UUID carId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("scheduleId") UUID scheduleId);
}
