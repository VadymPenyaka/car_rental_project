package nulp.cs.carrentalrestservice.modules.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository <Person, UUID> {
    Optional<Person> findByUsername(String username);

    boolean existsByUsername(String email);

    boolean existsByPhoneNumber (String phoneNumber);
}
