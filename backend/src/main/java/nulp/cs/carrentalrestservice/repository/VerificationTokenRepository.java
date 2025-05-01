package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
}
