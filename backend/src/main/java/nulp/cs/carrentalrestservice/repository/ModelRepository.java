package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Brand;
import nulp.cs.carrentalrestservice.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModelRepository extends JpaRepository <Model, UUID> {
    boolean existsByModelNameAndBrandName (String modelName, Brand brandName);

    Model findByModelNameAndBrandName(String modelName, Brand brandName);
}
