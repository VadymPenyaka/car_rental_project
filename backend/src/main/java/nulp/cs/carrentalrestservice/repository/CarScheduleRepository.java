package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarScheduleRepository extends JpaRepository<CarSchedule, Long> {

}
