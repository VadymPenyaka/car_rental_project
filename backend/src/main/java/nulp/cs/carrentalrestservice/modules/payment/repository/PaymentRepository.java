package nulp.cs.carrentalrestservice.modules.payment.repository;

import jakarta.persistence.LockModeType;
import nulp.cs.carrentalrestservice.modules.payment.enity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Optional<Payment> findByPaymentIntentId(String paymentIntentId);

    Optional<Payment> findBySessionId(String sessionId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Payment p WHERE p.id = :id")
    Optional<Payment> findByIdWithLock(@Param("id") UUID id);
}
