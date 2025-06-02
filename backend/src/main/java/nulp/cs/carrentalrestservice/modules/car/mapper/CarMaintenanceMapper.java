package nulp.cs.carrentalrestservice.modules.car.mapper;

import nulp.cs.carrentalrestservice.modules.car.enity.CarMaintenance;
import nulp.cs.carrentalrestservice.modules.car.dto.CarMaintenanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarMaintenanceMapper {
    @Mapping(target = "schedule", source = "schedule")
    CarMaintenanceDTO carMaintenanceToCarMaintenanceDTO(CarMaintenance carMaintenance);
    @Mapping(target = "schedule", source = "schedule")
    CarMaintenance carMaintenanceDTOToCarMaintenance(CarMaintenanceDTO carMaintenanceDTO);
}
