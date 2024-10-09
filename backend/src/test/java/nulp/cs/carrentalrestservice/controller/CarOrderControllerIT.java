package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.mapper.*;
import nulp.cs.carrentalrestservice.model.*;
import nulp.cs.carrentalrestservice.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CarOrderControllerIT {
    @Autowired
    private CarOrderController controller;

    @Autowired
    private CarOrderMapper carOrderMapper;

    @Autowired
    private CarOrderRepository carOrderRepository;

    @Autowired
    private CarScheduleRepository carScheduleRepository;

    @Autowired
    private CarScheduleMapper carScheduleMapper;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarMapper carMapper;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    @Transactional
    @Rollback
    void createCarOrder() {
        CarScheduleDTO carScheduleDTO = CarScheduleDTO.builder()
                .id(UUID.randomUUID())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .car(carMapper.carToCarDto(carRepository.findAll().get(0)))
                .build();

        CarOrderDTO carOrderDtoToSave = CarOrderDTO.builder()
                .id(UUID.randomUUID())
                .status(OrderStatus.APPROVED)
                .admin(adminMapper.adminToAdminDto(adminRepository.findAll().get(0)))
                .totalPrice(110.0)
                .customer(customerMapper.customerToCustomerDto(customerRepository.findAll().get(0)))
                .schedule(carScheduleDTO)
                .build();

        ResponseEntity responseEntity = controller.createCarOrder(carOrderDtoToSave);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(carOrderRepository.findById(carOrderDtoToSave.getId())).isNotNull();
    }

    @Test
    void getCarOrderById() {
        CarOrder expected = carOrderRepository.findAll().get(0);
        CarOrderDTO expectedDTO = carOrderMapper.carOrderToCarOrderDto(expected);

        CarOrderDTO actual = controller.getCarOrderById(expected.getId());

        assertThat(actual).isEqualTo(expectedDTO);
    }

    @Test
    @Rollback
    @Transactional
    void deleteCarOrderById () {
        CarOrderDTO carOrderDTO = carOrderMapper.carOrderToCarOrderDto(carOrderRepository.findAll().get(0));

        ResponseEntity responseEntity = controller.deleteCarOrderById(carOrderDTO.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(carOrderRepository.findById(carOrderDTO.getId())).isEqualTo(Optional.empty());
    }

    @Test
    @Rollback
    @Transactional
    void updateCarOrderById () {
        CarOrderDTO expected = carOrderMapper
                .carOrderToCarOrderDto(carOrderRepository.findAll().get(0));

        expected.setStatus(OrderStatus.APPROVED);

        ResponseEntity responseEntity = controller.updateCarOrderByID(expected.getId(), expected);
        CarOrderDTO actual = carOrderMapper.carOrderToCarOrderDto(carOrderRepository.findById(expected.getId()).get());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual).isEqualTo(expected);
    }

}