package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMaintenanceMapper {
    CarMaintenanceDTO carMaintenanceToCarMaintenanceDTO(CarMaintenance carMaintenance);
    CarMaintenance carMaintenanceDTOToCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
}
