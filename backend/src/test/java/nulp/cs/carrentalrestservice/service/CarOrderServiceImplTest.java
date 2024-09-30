package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapperImpl;
import nulp.cs.carrentalrestservice.model.*;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarOrderServiceImplTest {
    @Mock
    private CarOrderRepository carOrderRepository;
    @Mock
    private CarOrderMapperImpl carOrderMapper;
    @InjectMocks
    private CarOrderServiceImpl carOrderService;

    private CarOrder carOrder;
    private CarOrderDTO carOrderDTO;

    @BeforeEach
    void setUp() {
        Admin admin = Admin.builder()
                .password("password")
                .firstName("FirstName")
                .lastName("LastName")
                .email("email@gmail.com")
                .phoneNumber("123456789000")
                .build();

        AdminDTO adminDTO = AdminDTO.builder()
                .password("password")
                .firstName("FirstName")
                .lastName("LastName")
                .email("email@gmail.com")
                .phoneNumber("123456789000")
                .build();

        carOrder = CarOrder.builder()
                .status(OrderStatus.IN_USE)
                .admin(admin)
                .totalPrice(1000.0)
                .build();

        carOrderDTO = CarOrderDTO.builder()
                .status(OrderStatus.IN_USE)
                .admin(adminDTO)
                .totalPrice(1000.0)
                .build();


    }

    @Test
    void createCarOrder() {
        when(carOrderRepository.save(any())).thenReturn(carOrder);
        when(carOrderMapper.carOrderToCarOrderDto(any())).thenReturn(carOrderDTO);

        CarOrderDTO createdCarOrder = carOrderService.createCarOrder(carOrderDTO);

        assertThat(createdCarOrder).isNotNull();
        assertThat(createdCarOrder.getId()).isEqualTo(carOrderDTO.getId());
    }

    @Test
    void getCarOrderByID() {
        when(carOrderRepository.findById(any())).thenReturn(Optional.ofNullable(carOrder));
        when(carOrderMapper.carOrderToCarOrderDto(any())).thenReturn(carOrderDTO);

        CarOrderDTO foundCarOrder = carOrderService.getCarOrderByID(carOrderDTO.getId()).get();

        assertThat(foundCarOrder).isNotNull();
        assertThat(foundCarOrder.getId()).isEqualTo(carOrderDTO.getId());
    }

}