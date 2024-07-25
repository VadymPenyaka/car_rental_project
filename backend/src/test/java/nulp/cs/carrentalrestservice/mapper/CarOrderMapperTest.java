package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarOrderMapperTest {
    private final CarOrderMapper carOrderMapper = new CarOrderMapperImpl();

    @Test
    void carOrderDtoToCarOrder() {
        CarOrderDTO carOrderDTO = CarOrderDTO.builder()
                .status(OrderStatus.IN_USE)
                .build();

        CarOrder carOrder = carOrderMapper.carOrderDtoToCarOrder(carOrderDTO);

        assertThat(carOrder).isNotNull();
        assertThat(carOrder.getStatus()).isEqualTo(carOrderDTO.getStatus());
    }

    @Test
    void carOrderToCarOrderDto() {
        CarOrder carOrder = CarOrder.builder()
                .status(OrderStatus.IN_USE)
                .build();

        CarOrderDTO carOrderDTO = carOrderMapper.carOrderToCarOrderDto(carOrder);

        assertThat(carOrder).isNotNull();
        assertThat(carOrder.getStatus()).isEqualTo(carOrderDTO.getStatus());
    }
}