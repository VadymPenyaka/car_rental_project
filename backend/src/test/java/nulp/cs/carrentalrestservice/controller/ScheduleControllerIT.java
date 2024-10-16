package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.ScheduleStatus;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ScheduleControllerIT {
    @Autowired
    private ScheduleController scheduleController;
    @Autowired
    private CarScheduleRepository carScheduleRepository;
    @Autowired
    private CarScheduleMapper carScheduleMapper;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarMapper carMapper;

    @Test
    void getScheduleById() {
        CarScheduleDTO carScheduleDto = carScheduleMapper
                .carScheduleToCarScheduleDTO(carScheduleRepository.findAll().get(0));

        CarScheduleDTO result = scheduleController.getScheduleById(carScheduleDto.getId());

        assertThat(result).isEqualTo(carScheduleDto);
    }

    @Test
    @Rollback
    @Transactional
    void createSchedule() {
        CarScheduleDTO carScheduleDTO = CarScheduleDTO.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .car(carMapper.carToCarDto(carRepository.findAll().get(0)))
                .build();

        ResponseEntity result = scheduleController.createSchedule(carScheduleDTO);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(carScheduleRepository.count()).isEqualTo(3);
    }

    @Test
    @Rollback
    @Transactional
    void updateScheduleByIdById() {
        CarScheduleDTO carScheduleDTO = carScheduleMapper
                .carScheduleToCarScheduleDTO(carScheduleRepository.findAll().get(0));
        carScheduleDTO.setStartDate(carScheduleDTO.getStartDate().plusDays(1));

        ResponseEntity response = scheduleController.updateScheduleById(carScheduleDTO.getId(), carScheduleDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        CarSchedule carSchedule = carScheduleRepository.findById(carScheduleDTO.getId()).get();
        assertThat(carScheduleMapper.carScheduleToCarScheduleDTO(carSchedule)).isEqualTo(carScheduleDTO);
    }

    @Test
    @Rollback
    @Transactional
    void deleteScheduleById() {
        UUID idToDelete = carScheduleRepository.findAll().get(0).getId();


        ResponseEntity response = scheduleController.deleteScheduleById(idToDelete);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}