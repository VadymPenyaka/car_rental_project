package nulp.cs.carrentalrestservice.modules.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonPendingConfirmationRepository extends JpaRepository<PersonPendingConfirmation, UUID> {
}
