package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.bankid.DriverLicenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LicenseCategoryRepository extends JpaRepository<DriverLicenseCategory, UUID> {
}
