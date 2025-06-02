package nulp.cs.carrentalrestservice.modules.car.repository;

import nulp.cs.carrentalrestservice.modules.car.enity.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CarMaintenanceRepository extends JpaRepository <CarMaintenance, UUID> {
}
