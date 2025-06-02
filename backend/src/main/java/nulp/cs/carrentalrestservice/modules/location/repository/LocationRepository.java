package nulp.cs.carrentalrestservice.modules.location.repository;

import nulp.cs.carrentalrestservice.modules.location.enity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LocationRepository extends JpaRepository <Location, UUID> {

}
