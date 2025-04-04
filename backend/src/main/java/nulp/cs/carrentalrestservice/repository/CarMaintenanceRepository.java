package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CarMaintenanceRepository extends JpaRepository <CarMaintenance, UUID> {
}
