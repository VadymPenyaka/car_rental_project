package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, String> {
}
