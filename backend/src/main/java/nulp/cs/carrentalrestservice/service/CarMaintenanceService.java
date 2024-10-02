package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;

import java.util.List;
import java.util.Optional;

public interface CarMaintenanceService {
    Optional<CarMaintenanceDTO> getCarMaintenanceById(Long id);
    List<CarMaintenanceDTO> getAllCarMaintenanceByCarId(Long carId);
    CarMaintenanceDTO createCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
    Optional<CarMaintenance> updateCarMaintenanceById(Long id, CarMaintenanceDTO carMaintenanceDTO);
    boolean deleteCarMaintenanceById(Long id);
}
