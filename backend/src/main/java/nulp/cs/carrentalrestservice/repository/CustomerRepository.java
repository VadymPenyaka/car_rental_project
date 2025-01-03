package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phone);
}
