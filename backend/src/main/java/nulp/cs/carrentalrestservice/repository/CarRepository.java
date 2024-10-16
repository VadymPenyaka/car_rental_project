package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    @Query("SELECT c FROM Car c WHERE NOT EXISTS (" +
            "SELECT 1 FROM CarSchedule s WHERE s.car.id = c.id " +
            "AND s.startDate < :endDate AND s.endDate > :startDate)")
    List<Car> findAllAvailableOnPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Car c WHERE " +
            "(:location IS NULL OR c.location = :location) AND " +
            "(:carClass IS NULL OR c.carClass = :carClass) AND " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:gearboxType IS NULL OR c.gearboxType = :gearboxType) AND " +
            "(:fuelType IS NULL OR c.fuelType = :fuelType) AND " +
            "((:startDate IS NULL AND :endDate IS NULL) OR " +
            "NOT EXISTS (SELECT s FROM CarSchedule s WHERE s.car.id = c.id AND" +
            "(:startDate IS NULL OR s.endDate >= :startDate) AND " +
            "(:endDate IS NULL OR s.startDate <= :endDate)))" )
    List<Car> findAllCarsByCriteria ( @Param("location") UUID location,
                                      @Param("carClass") CarClass carClass,
                                      @Param("brand") String brand,
                                      @Param("gearboxType") GearboxType gearboxType,
                                      @Param("fuelType") FuelType fuelType,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);
}
