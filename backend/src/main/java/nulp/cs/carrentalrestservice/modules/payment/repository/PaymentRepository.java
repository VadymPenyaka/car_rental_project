package nulp.cs.carrentalrestservice.modules.payment.repository;

import nulp.cs.carrentalrestservice.modules.payment.enity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
