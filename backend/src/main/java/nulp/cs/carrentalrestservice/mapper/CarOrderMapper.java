package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarOrderMapper {
    CarOrder carOrderDtoToCarOrder (CarOrderDTO carOrderDTO);
    CarOrderDTO carOrderToCarOrderDto (CarOrder carOrder);
}
