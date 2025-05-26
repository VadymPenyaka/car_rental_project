package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CarOrderMapper {
    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "schedule", target = "schedule")
    @Mapping(source = "person", target = "person")
    CarOrder carOrderDtoToCarOrder(CarOrderDTO carOrderDTO);

    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "schedule", target = "schedule")
    @Mapping(source = "person", target = "person")
    CarOrderDTO carOrderToCarOrderDto(CarOrder carOrder);
}
