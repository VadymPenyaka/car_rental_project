package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMaintenanceMapper {
    @Mapping(target = "schedule", source = "schedule")
    CarMaintenanceDTO carMaintenanceToCarMaintenanceDTO(CarMaintenance carMaintenance);
    @Mapping(target = "schedule", source = "schedule")
    CarMaintenance carMaintenanceDTOToCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
}
