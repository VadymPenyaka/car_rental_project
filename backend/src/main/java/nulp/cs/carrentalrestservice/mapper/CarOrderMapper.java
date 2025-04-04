package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarOrderMapper {
    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "schedule", target="schedule")
    CarOrder carOrderDtoToCarOrder (CarOrderDTO carOrderDTO);
    @Mapping(source = "admin", target = "admin")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "schedule", target="schedule")
    CarOrderDTO carOrderToCarOrderDto (CarOrder carOrder);
}
