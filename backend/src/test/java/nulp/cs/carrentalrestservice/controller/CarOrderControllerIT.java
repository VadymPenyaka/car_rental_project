package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.mapper.AdminMapper;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CarOrderControllerIT {
    @Autowired
    private CarOrderController controller;

    @Autowired
    private CarOrderMapper carOrderMapper;

    @Autowired
    private CarOrderRepository carOrderRepository;

    @Autowired
    private CarRepository carRepository;



    @Test
    void createCarOrder() {
        CarOrderDTO carOrderDtoToSave = carOrderMapper
                .carOrderToCarOrderDto(carOrderRepository.findAll().get(0));
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
    void getOrdersByStatus () {
        List<CarOrderDTO> expected = Arrays.asList(carOrderMapper
                .carOrderToCarOrderDto(carOrderRepository.findAll().get(0)));

        List<CarOrderDTO> actual = controller.getAllCarOrdersByStatus(OrderStatus.IN_USE);

        assertThat(actual).isEqualTo(expected);
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

    @Test
    @Transactional
    void getAllCarOrdersByStatus () {
        CarOrderDTO expected = carOrderMapper
                .carOrderToCarOrderDto(carOrderRepository.findAll().get(0));

        List<CarOrderDTO> actual = controller.getAllCarOrdersByStatus(expected.getStatus());

        assertThat(actual.size()).isEqualTo(1);
    }

}