package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarScheduleDTO;

import java.util.List;
import java.util.Optional;

public interface CarScheduleService {
    Optional<CarScheduleDTO> getCarScheduleById(Long id);

    Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, Long id);

    CarScheduleDTO createCarSchedule(CarScheduleDTO carSchedule);

    boolean deleteCarScheduleById(Long id);

}
