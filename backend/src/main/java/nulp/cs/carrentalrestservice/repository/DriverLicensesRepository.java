package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.bankid.DriverLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DriverLicensesRepository extends JpaRepository <DriverLicense, UUID> {
    boolean existsByDocumentNumber (String documentNumber);
}
