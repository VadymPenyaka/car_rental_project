package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.CarClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
}
