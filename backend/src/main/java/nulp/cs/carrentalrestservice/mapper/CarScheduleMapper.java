package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.model.dto.CarScheduleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarScheduleMapper {
    CarSchedule carScheduleDtoToCarSchedule(CarScheduleDTO carScheduleDTO);
    CarScheduleDTO carScheduleToCarScheduleDTO(CarSchedule carSchedule);
}
