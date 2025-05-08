package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.PersonPendingConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonPendingConfirmationRepository extends JpaRepository<PersonPendingConfirmation, UUID> {
}
