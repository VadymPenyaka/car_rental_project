package nulp.cs.carrentalrestservice.modules.car.serivce;

import nulp.cs.carrentalrestservice.modules.car.CarScheduleDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;

import java.util.Optional;
import java.util.UUID;

public interface ScheduleService {
    Optional<CarScheduleDTO> getCarScheduleById(UUID id);

    Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, UUID id);

    CarScheduleDTO createCarScheduleForCarOrder(OrderCreationRequest orderRequest);

    boolean isCarBooked(CarScheduleDTO carSchedule, UUID excludeScheduleId);

}
