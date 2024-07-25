package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.OrderDetail;
import nulp.cs.carrentalrestservice.model.OrderDetailDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderDetailMapperTest {

    private final OrderDetailMapper orderDetailMapper= new OrderDetailMapperImpl();
    @Test
    void orderDetailDtoToOrderDetailTest() {
        OrderDetailDTO orderDetailDTO = OrderDetailDTO.builder()
                .id(Long.valueOf(1))
                .pickUpDate(LocalDate.now())
                .pickUpLocation("Lviv")
                .dropOffLocation("Lviv")
                .dropOffDate(LocalDate.now())
                .numberOfDays(1)
                .totalPrice(220.0)
                .build();

        OrderDetail orderDetail = orderDetailMapper.orderDetailDtoToOrderDetail(orderDetailDTO);

        assertThat(orderDetail.getId()).isEqualTo(orderDetailDTO.getId());
        assertThat(orderDetail.getDropOffDate()).isEqualTo(orderDetailDTO.getDropOffDate());
    }

    @Test
    void orderDetailToOrderDetailDtoTest() {
        OrderDetail orderDetail = OrderDetail.builder()
                .id(Long.valueOf(1))
                .pickUpDate(LocalDate.now())
                .pickUpLocation("Lviv")
                .dropOffLocation("Lviv")
                .dropOffDate(LocalDate.now())
                .numberOfDays(1)
                .totalPrice(220.0)
                .build();

        OrderDetailDTO orderDetailDTO = orderDetailMapper.orderDetailToOrderDetailDto(orderDetail);

        assertThat(orderDetail.getId()).isEqualTo(orderDetailDTO.getId());
        assertThat(orderDetail.getDropOffDate()).isEqualTo(orderDetailDTO.getDropOffDate());
    }
}