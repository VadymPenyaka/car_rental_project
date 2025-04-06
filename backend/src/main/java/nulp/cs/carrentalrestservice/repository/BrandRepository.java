package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, String> {
    @Query("SELECT DISTINCT c.model.brandName FROM Car c")
    List<Brand> getAllAvailableBrands ();
}
