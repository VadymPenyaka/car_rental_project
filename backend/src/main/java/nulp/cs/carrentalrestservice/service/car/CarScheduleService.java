package nulp.cs.carrentalrestservice.service.car;

import nulp.cs.carrentalrestservice.model.dto.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.util.Optional;
import java.util.UUID;

public interface CarScheduleService {
    Optional<CarScheduleDTO> getCarScheduleById(UUID id);

    Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, UUID id);

    CarScheduleDTO createCarScheduleForCarOrder(OrderCreationRequest orderRequest);

    boolean deleteCarScheduleById(UUID id);

    boolean isCarBooked(CarScheduleDTO carSchedule, UUID excludeScheduleId);

}
