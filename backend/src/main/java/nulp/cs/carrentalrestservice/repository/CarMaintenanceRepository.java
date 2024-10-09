package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarMaintenanceRepository extends JpaRepository<CarMaintenance, UUID> {
}
