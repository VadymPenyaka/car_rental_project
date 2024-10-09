package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarOrderDTO;

import java.util.Optional;
import java.util.UUID;

public interface CarOrderService {
    CarOrderDTO createCarOrder (CarOrderDTO carOrderDTO);

    Optional<CarOrderDTO> getCarOrderByID (UUID id);

    boolean deleteCarOrderById (UUID id);

    Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO);

}
