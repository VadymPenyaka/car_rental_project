package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {
    CarDTO createCar (CarDTO carDTO);

    Optional<CarDTO> getCarByID (Long id);

    List<CarDTO> getAllCars ();

    Boolean deleteCarById (Long id);

    Optional<CarDTO> updateCarByID(Long id, CarDTO carDTO);

    List<CarDTO> getCarsByCarClass (String carClass);

}
