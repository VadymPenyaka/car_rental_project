package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Brand;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository <Car, UUID> {
    @Query("SELECT c FROM Car c WHERE " +
            "(:carClass IS NULL OR c.carClass = :carClass) AND " +
            "(:gearboxType IS NULL OR c.gearboxType = :gearboxType) AND " +
            "(:fuelType IS NULL OR c.fuelType = :fuelType) AND " +
            ":brand IS NULL OR c.model.brandName.name = :brand AND" +
            "((:startDate IS NULL AND :endDate IS NULL) OR " +
            "NOT EXISTS (SELECT s FROM CarSchedule s WHERE s.car.id = c.id AND" +
            "(:startDate IS NULL OR s.endDate >= :startDate) AND " +
            "(:endDate IS NULL OR s.startDate <= :endDate)))" )
    List<Car> findAllCarsByCriteria(@Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate,
                                    @Param("carClass") CarClass carClass,
                                    @Param("gearboxType") GearboxType gearboxType,
                                    @Param("fuelType") FuelType fuelType,
                                    @Param("brand") String brand);
}
