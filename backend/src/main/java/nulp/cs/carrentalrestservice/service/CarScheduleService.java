package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarScheduleDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarScheduleService {
    Optional<CarScheduleDTO> getCarScheduleById(UUID id);

    Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, UUID id);

    CarScheduleDTO createCarSchedule(CarScheduleDTO carSchedule);

    boolean deleteCarScheduleById(UUID id);

}
