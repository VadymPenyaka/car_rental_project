package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface CarScheduleRepository extends JpaRepository<CarSchedule, Long> {
    @Query("SELECT COUNT(s) > 0 FROM CarSchedule s WHERE s.car.id =:carId AND s.startDate <= :endDate AND s.endDate >= :startDate")
    boolean isCarBooked (@Param("carId") Long carId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
