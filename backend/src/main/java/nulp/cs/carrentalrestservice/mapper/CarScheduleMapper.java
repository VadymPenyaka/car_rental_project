package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarScheduleMapper {
    CarSchedule carScheduleDtoToCarSchedule(CarScheduleDTO carScheduleDTO);
    CarScheduleDTO carScheduleToCarScheduleDTO(CarSchedule carSchedule);
}
