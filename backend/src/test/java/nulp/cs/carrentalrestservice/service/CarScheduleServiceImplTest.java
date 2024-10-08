package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.ScheduleStatus;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarScheduleServiceImplTest {
    @InjectMocks
    private CarScheduleServiceImpl carScheduleService;
    @Mock
    private CarScheduleRepository carScheduleRepository;
    @Mock
    private CarScheduleMapper carScheduleMapper;

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
    }

    @Test
    void getCarScheduleById() {
        when(carScheduleRepository.findById(any())).thenReturn(Optional.of(carSchedule));
        when(carScheduleMapper.carScheduleToCarScheduleDTO(carSchedule)).thenReturn(carScheduleDTO);

        CarScheduleDTO result = carScheduleService.getCarScheduleById(carSchedule.getId()).get();

        assertThat(carScheduleDTO).isEqualTo(result);
    }

    @Test
    void updateCarScheduleById() {
        CarScheduleDTO updated = CarScheduleDTO.builder()
                .car(CarDTO.builder().build())
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(1))
                .status(ScheduleStatus.BOOKED)
                .build();

        when(carScheduleRepository.findById(any())).thenReturn(Optional.of(carSchedule));
        when(carScheduleRepository.save(any())).thenReturn(carSchedule);
        when(carScheduleMapper.carScheduleToCarScheduleDTO(carSchedule)).thenReturn(updated);
        when(carScheduleMapper.carScheduleDtoToCarSchedule(any())).thenReturn(carSchedule);

        CarScheduleDTO result = carScheduleService.updateCarScheduleById(updated, carSchedule.getId()).get();

        assertThat(updated).isEqualTo(carScheduleDTO);
    }

    @Test
    void createCarSchedule() {
        when(carScheduleRepository.save(any())).thenReturn(carSchedule);
        when(carScheduleMapper.carScheduleToCarScheduleDTO(carSchedule)).thenReturn(carScheduleDTO);

        CarScheduleDTO result = carScheduleService.createCarSchedule(carScheduleDTO);

        assertThat(carScheduleDTO).isEqualTo(result);
    }

    @Test
    void deleteCarScheduleById() {
        when(carScheduleRepository.existsById(any())).thenReturn(true);

        boolean result = carScheduleService.deleteCarScheduleById(carSchedule.getId());

        verify(carScheduleRepository).deleteById(carSchedule.getId());
        assertThat(result).isTrue();
    }
}