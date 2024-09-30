package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, Long> {

}
