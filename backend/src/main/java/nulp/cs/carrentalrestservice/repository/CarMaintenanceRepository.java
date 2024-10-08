package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, Long> {
}
