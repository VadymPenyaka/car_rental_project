package nulp.cs.carrentalrestservice.modules.car.repository;

import nulp.cs.carrentalrestservice.modules.car.enity.Brand;
import nulp.cs.carrentalrestservice.modules.car.enity.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModelRepository extends JpaRepository <Model, UUID> {
    boolean existsByModelNameAndBrandName (String modelName, Brand brandName);

    Model findByModelNameAndBrandName(String modelName, Brand brandName);
}
