package nulp.cs.carrentalrestservice.modules.car.serivce;

import nulp.cs.carrentalrestservice.modules.car.dto.CarMaintenanceDTO;

import java.util.Optional;
import java.util.UUID;

public interface CarMaintenanceService {
    Optional<CarMaintenanceDTO> getCarMaintenanceById(UUID id);
    void createCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
    Optional<CarMaintenanceDTO> updateCarMaintenanceById(UUID id, CarMaintenanceDTO carMaintenanceDTO);
    boolean deleteCarMaintenanceById(UUID id);
}
