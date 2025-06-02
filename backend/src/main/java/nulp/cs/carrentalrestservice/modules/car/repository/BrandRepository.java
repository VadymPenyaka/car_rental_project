package nulp.cs.carrentalrestservice.modules.car.repository;

import nulp.cs.carrentalrestservice.modules.car.enity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, String> {
    @Query("SELECT DISTINCT c.model.brandName FROM Car c")
    List<Brand> getAllAvailableBrands ();
}
