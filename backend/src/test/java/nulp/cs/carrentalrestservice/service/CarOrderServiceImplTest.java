package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.*;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapperImpl;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.*;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

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
    private CarScheduleRepository carScheduleRepository;
    @Mock
    private CarScheduleMapper carScheduleMapper;
    @Mock
    private CarOrderMapperImpl carOrderMapper;
    @Mock
    private CarScheduleService carScheduleService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;
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

        Customer customer =  Customer.builder().email("email@gmail.com").firstName("FirstName").build();
        CustomerDTO customerDTO = CustomerDTO.builder().build();

        CarScheduleDTO carScheduleDTO = CarScheduleDTO.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .build();

        CarSchedule carSchedule = CarSchedule.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .build();

        carOrder = CarOrder.builder()
                .status(OrderStatus.IN_USE)
                .admin(admin)
                .customer(customer)
                .schedule(carSchedule)
                .totalPrice(1000.0)
                .build();

        carOrderDTO = CarOrderDTO.builder()
                .status(OrderStatus.IN_USE)
                .admin(adminDTO)
                .customer(customerDTO)
                .totalPrice(1000.0)
                .schedule(carScheduleDTO)
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