package nulp.cs.carrentalrestservice.modules.order.mapper;

import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.enity.CarOrder;
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
