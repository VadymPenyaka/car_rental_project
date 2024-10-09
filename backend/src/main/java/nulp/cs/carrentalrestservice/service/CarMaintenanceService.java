package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;

import java.util.Optional;
import java.util.UUID;

public interface CarMaintenanceService {
    Optional<CarMaintenanceDTO> getCarMaintenanceById(UUID id);
    CarMaintenanceDTO createCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
    Optional<CarMaintenanceDTO> updateCarMaintenanceById(UUID id, CarMaintenanceDTO carMaintenanceDTO);
    boolean deleteCarMaintenanceById(UUID id);
}
