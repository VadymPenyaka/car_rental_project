package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.mapper.CarMaintenanceMapper;
import nulp.cs.carrentalrestservice.model.*;
import nulp.cs.carrentalrestservice.repository.CarMaintenanceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class MaintenanceControllerIT {
    @Autowired
    private MaintenanceController maintenanceController;
    @Autowired
    private CarMaintenanceMapper maintenanceMapper;
    @Autowired
    private CarMaintenanceRepository repository;

    @Test
    void getMaintenanceById() {
        CarMaintenanceDTO maintenanceDTO = maintenanceMapper
                .carMaintenanceToCarMaintenanceDTO(repository.findAll().get(0));

        CarMaintenanceDTO result = maintenanceController
                .getMaintenanceById(maintenanceDTO.getId());

        assertThat(result).isEqualTo(maintenanceDTO);
    }

    @Test
    @Rollback
    @Transactional
    void createMaintenance() {
        CarMaintenanceDTO maintenanceDTO = maintenanceMapper
                .carMaintenanceToCarMaintenanceDTO(repository.findAll().get(0));

        CarScheduleDTO carScheduleDTO = maintenanceDTO.getSchedule();
        carScheduleDTO.setStartDate(LocalDate.now().plusYears(10));
        carScheduleDTO.setEndDate(LocalDate.now().plusYears(10));
        maintenanceDTO.setSchedule(carScheduleDTO);

        ResponseEntity responseEntity = maintenanceController.createMaintenance(maintenanceDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Rollback
    @Transactional
    void updateMaintenanceById() {
        CarMaintenanceDTO maintenanceDTO = maintenanceMapper
                .carMaintenanceToCarMaintenanceDTO(repository.findAll().get(0));

        maintenanceDTO.setDescription("updated description");

        ResponseEntity responseEntity = maintenanceController.
                updateMaintenanceById(maintenanceDTO.getId(), maintenanceDTO);
        CarMaintenanceDTO result = maintenanceController.getMaintenanceById(maintenanceDTO.getId());

        assertThat(result).isEqualTo(maintenanceDTO);
        assertThat(result.getDescription()).isEqualTo("updated description");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Rollback
    @Transactional
    void deleteMaintenanceById() {
        CarMaintenanceDTO maintenanceDTO = maintenanceMapper
                .carMaintenanceToCarMaintenanceDTO(repository.findAll().get(0));

        ResponseEntity responseEntity = maintenanceController.deleteMaintenanceById(maintenanceDTO.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(repository.findById(maintenanceDTO.getId())).isEmpty();
    }
}