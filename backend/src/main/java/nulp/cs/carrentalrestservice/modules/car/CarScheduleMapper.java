package nulp.cs.carrentalrestservice.modules.car;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarScheduleMapper {
    CarSchedule carScheduleDtoToCarSchedule(CarScheduleDTO carScheduleDTO);
    CarScheduleDTO carScheduleToCarScheduleDTO(CarSchedule carSchedule);
}
