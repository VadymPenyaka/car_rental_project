package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PassportRepository extends JpaRepository <Passport, UUID> {
    boolean existsByDocumentNumber (String documentNumber);
}
