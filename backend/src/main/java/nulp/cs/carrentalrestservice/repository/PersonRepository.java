package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository <Person, UUID> {
    Optional<Person> findByUsername(String username);

    boolean existsByUsername(String email);

    boolean existsByPhoneNumber (String phoneNumber);
}
