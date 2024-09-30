package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;

public interface CarMaintenanceMapper {
    CarMaintenanceDTO carMaintenanceToCarMaintenanceDTO(CarMaintenance carMaintenance);
    CarMaintenance carMaintenanceDTOToCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
}
