package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverLicensesRepository extends JpaRepository <DriverLicense, UUID> {
}
