package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;

import java.util.Optional;

public interface CarMaintenanceService {
    Optional<CarMaintenanceDTO> getCarMaintenanceById(Long id);
    CarMaintenanceDTO createCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
    Optional<CarMaintenanceDTO> updateCarMaintenanceById(Long id, CarMaintenanceDTO carMaintenanceDTO);
    boolean deleteCarMaintenanceById(Long id);
}
