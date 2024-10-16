package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarService {
    CarDTO createCar (CarDTO carDTO);

    Optional<CarDTO> getCarByID (UUID id);

    List<CarDTO> getAllCarsByCriteria(UUID locationId,
                                      CarClass carClass,
                                      String brand,
                                      GearboxType gearboxType,
                                      FuelType fuelType,
                                      LocalDate startDate,
                                      LocalDate endDate);

    Boolean deleteCarById (UUID id);

    Optional<CarDTO> updateCarByID(UUID id, CarDTO carDTO);

}
