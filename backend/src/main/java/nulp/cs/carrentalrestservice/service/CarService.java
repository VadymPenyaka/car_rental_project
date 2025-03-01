package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequestDto;
import nulp.cs.carrentalrestservice.model.response.CarCardDTO;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarService {
    CarDTO createCar (CarDTO carDTO);

    List<CarCardDTO> getAllCarsByCriteria(CarSearchRequestDto carDto);

    Boolean deleteCarById (UUID id);

    Optional<CarDTO> updateCarByID(UUID id, CarDTO carDTO);

    Optional<CarCustomerDetailsDTO> getCarCustomerDetailsById(UUID id);

    Optional<CarDTO> getCarFullDetailsById(UUID id);
}
