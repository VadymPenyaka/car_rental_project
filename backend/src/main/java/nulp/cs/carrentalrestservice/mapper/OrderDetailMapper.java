package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.OrderDetail;
import nulp.cs.carrentalrestservice.model.OrderDetailDTO;
import org.mapstruct.Mapper;

@Mapper
public interface OrderDetailMapper {
    OrderDetail orderDetailDtoToOrderDetail (OrderDetailDTO orderDetailDTO);

    OrderDetailDTO orderDetailToOrderDetailDto (OrderDetail orderDetail);
}
