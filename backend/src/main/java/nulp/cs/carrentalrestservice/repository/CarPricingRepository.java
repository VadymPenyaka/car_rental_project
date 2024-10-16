package nulp.cs.carrentalrestservice.repository;


import nulp.cs.carrentalrestservice.entity.CarPricing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarPricingRepository extends JpaRepository<CarPricing, UUID> {
}
