package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.entity.CarMaintenance;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.mapper.CarMaintenanceMapper;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.ScheduleStatus;
import nulp.cs.carrentalrestservice.repository.CarMaintenanceRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarMaintenanceServiceImplTest {
    @InjectMocks
    private CarMaintenanceServiceImpl carMaintenanceService;
    @Mock
    private CarMaintenanceRepository carMaintenanceRepository;
    @Mock
    private CarMaintenanceMapper carMaintenanceMapper;
    @Mock
    private CarScheduleServiceImpl carScheduleService;
    @Mock
    private CarScheduleMapper carScheduleMapper;
    private CarMaintenance carMaintenance;
    private CarMaintenanceDTO carMaintenanceDTO;
    private CarSchedule carSchedule;
    private CarScheduleDTO carScheduleDTO;

    @BeforeEach
    void setUp() {
        carSchedule = CarSchedule.builder()
                .car(Car.builder().build())
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .build();

        carScheduleDTO = CarScheduleDTO.builder()
                .car(CarDTO.builder().build())
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .build();


        carMaintenance = CarMaintenance.builder()
                .price(120.0)
                .schedule(carSchedule)
                .description("description")
                .build();

        carMaintenanceDTO = CarMaintenanceDTO.builder()
                .price(120.0)
                .schedule(carScheduleDTO)
                .description("description")
                .build();
    }

    @Test
    void getCarMaintenanceById() {
        when(carMaintenanceRepository.findById(any())).thenReturn(Optional.of(carMaintenance));
        when(carMaintenanceMapper.carMaintenanceToCarMaintenanceDTO(carMaintenance)).thenReturn(carMaintenanceDTO);

        CarMaintenanceDTO result = carMaintenanceService.getCarMaintenanceById(carMaintenance.getId()).get();

        assertThat(carMaintenanceDTO).isEqualTo(result);
    }

    @Test
    void createCarMaintenance() {
        when(carMaintenanceRepository.save(any())).thenReturn(carMaintenance);
        when(carMaintenanceMapper.carMaintenanceToCarMaintenanceDTO(carMaintenance)).thenReturn(carMaintenanceDTO);

        CarMaintenanceDTO result = carMaintenanceService.createCarMaintenance(carMaintenanceDTO);

        assertThat(carMaintenanceDTO).isEqualTo(result);
    }

    @Test
    void updateCarMaintenanceById() {
        CarMaintenanceDTO updated = CarMaintenanceDTO.builder()
                .price(120.0)
                .schedule(carScheduleDTO)
                .description("updated")
                .build();

        when(carMaintenanceRepository.findById(any())).thenReturn(Optional.of(carMaintenance));
        when(carMaintenanceRepository.save(any())).thenReturn(carMaintenance);
        when(carMaintenanceMapper.carMaintenanceToCarMaintenanceDTO(carMaintenance)).thenReturn(updated);
        when(carScheduleService.updateCarScheduleById(any(), any())).thenReturn(Optional.of(carScheduleDTO));

        Optional<CarMaintenanceDTO> result = carMaintenanceService.updateCarMaintenanceById(carMaintenance.getId(), updated);

        assertThat(result.get()).isEqualTo(updated);
    }

    @Test
    void deleteCarMaintenanceById() {
        when(carMaintenanceRepository.existsById(any())).thenReturn(true);

        boolean result = carMaintenanceService.deleteCarMaintenanceById(carMaintenance.getId());

        verify(carMaintenanceRepository).deleteById(carMaintenance.getId());
        assertThat(result).isTrue();
    }
}