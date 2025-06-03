package nulp.cs.carrentalrestservice.modules.person.repository;

import nulp.cs.carrentalrestservice.modules.person.entity.PersonPendingConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonPendingConfirmationRepository extends JpaRepository<PersonPendingConfirmation, UUID> {
}
