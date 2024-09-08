package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.bootstrap.Bootstrap;
import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.entity.OrderDetail;
import nulp.cs.carrentalrestservice.mapper.OrderDetailMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.OrderDetailDTO;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import nulp.cs.carrentalrestservice.repository.OrderDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Import(Bootstrap.class)
class OrderDetailServiceImplTest {
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private OrderDetailMapper orderDetailMapper;
    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    private OrderDetail orderDetail;

    private OrderDetailDTO orderDetailDTO;

    @BeforeEach
    void setUp() {
        orderDetail = OrderDetail.builder()
                .totalPrice(123.2)
                .car(null)
                .build();

        orderDetailDTO = OrderDetailDTO.builder()
                .totalPrice(123.2)
                .car(null)
                .build();
    }

    @Test
    void createOrderDetail() {
        when(orderDetailRepository.save(any())).thenReturn(orderDetail);
        when(orderDetailMapper.orderDetailToOrderDetailDto(any())).thenReturn(orderDetailDTO);

        OrderDetailDTO savedOrderDetail= orderDetailService.createOrderDetail(orderDetailDTO);

        assertThat(savedOrderDetail).isNotNull();
    }

    @Test
    void getOrderDetailById() {
        when(orderDetailRepository.findById(any())).thenReturn(Optional.of(orderDetail));
        when(orderDetailMapper.orderDetailToOrderDetailDto(any())).thenReturn(orderDetailDTO);

        OrderDetailDTO savedOrderDetail = orderDetailService.getOrderDetailById(orderDetailDTO.getId()).get();

        assertThat(orderDetail).isNotNull();
        assertThat(orderDetail.getId()).isEqualTo(savedOrderDetail.getId());
    }

    @Test
    void updateOrderDetail() {
        OrderDetail expectedOrderDetail = OrderDetail.builder()
                .totalPrice(123.2)
                .car(null)
                .build();


// TODO

        when(orderDetailRepository.findById(any())).thenReturn(Optional.ofNullable(expectedOrderDetail));


        orderDetailDTO.setTotalPrice(expectedOrderDetail.getTotalPrice());
        when(orderDetailRepository.save(any())).thenReturn(expectedOrderDetail);
        when(orderDetailMapper.orderDetailToOrderDetailDto(any())).thenReturn(orderDetailDTO);
//
        OrderDetailDTO updatedOrderDetail = orderDetailService.updateOrderDetail(orderDetailDTO, orderDetail.getId()).get();
//
        assertThat(updatedOrderDetail).isNotNull();
        assertThat(updatedOrderDetail.getTotalPrice()).isEqualTo(expectedOrderDetail.getTotalPrice());
        assertThat(updatedOrderDetail.getId()).isEqualTo(orderDetailDTO.getId());
    }
}