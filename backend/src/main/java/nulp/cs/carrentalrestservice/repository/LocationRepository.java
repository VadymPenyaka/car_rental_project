package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {

}
