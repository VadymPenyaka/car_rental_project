package nulp.cs.carrentalrestservice.modules.car.repository;

import nulp.cs.carrentalrestservice.modules.car.enity.CarRegistrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRegistrationInfoRepository extends JpaRepository<CarRegistrationInfo, UUID> {
    boolean existsByVin (String vin);
}
