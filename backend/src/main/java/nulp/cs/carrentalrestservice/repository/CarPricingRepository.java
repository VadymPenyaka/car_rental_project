package nulp.cs.carrentalrestservice.repository;


import nulp.cs.carrentalrestservice.entity.CarPricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarPricingRepository extends JpaRepository<CarPricing, Long> {
    Optional<CarPricing> findByCarId (Long carId);
}
