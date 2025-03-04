package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CustomerRepository extends JpaRepository <Customer, UUID> {
    boolean existsByPassportId(String passportId);
}

