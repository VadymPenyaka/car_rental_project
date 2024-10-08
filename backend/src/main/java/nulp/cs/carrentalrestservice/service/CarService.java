package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.FuelType;
import nulp.cs.carrentalrestservice.model.GearboxType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarService {
    CarDTO createCar (CarDTO carDTO);

    Optional<CarDTO> getCarByID (Long id);

    List<CarDTO> getAllCarsByCriteria(Long locationId,
                                      CarClass carClass,
                                      String brand,
                                      GearboxType gearboxType,
                                      FuelType fuelType,
                                      LocalDate startDate,
                                      LocalDate endDate);

    Boolean deleteCarById (Long id);

    Optional<CarDTO> updateCarByID(Long id, CarDTO carDTO);

}
