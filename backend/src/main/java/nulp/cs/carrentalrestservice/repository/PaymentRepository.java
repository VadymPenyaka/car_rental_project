package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
