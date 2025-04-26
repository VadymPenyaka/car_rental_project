package nulp.cs.carrentalrestservice.service.car;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.dto.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.ScheduleStatus;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarScheduleServiceImpl implements CarScheduleService {
    private final CarScheduleRepository carScheduleRepository;
    private final CarScheduleMapper carScheduleMapper;
    private final LoggingService loggingService;
    private final CarService carService;

    @Override
    public Optional<CarScheduleDTO> getCarScheduleById(UUID id) {
        loggingService.logInfo("Getting car schedule for ID: " + id);
        return Optional.ofNullable(carScheduleMapper
                .carScheduleToCarScheduleDTO(carScheduleRepository.findById(id).get()));
    }

    @Override
    public Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, UUID id) {
        loggingService.logInfo("Updating car schedule for ID: " + id);
        if (isCarBooked(carScheduleDTO, id)) {
            throw new IllegalArgumentException("Car already booked for this period!");
        }


        AtomicReference<Optional<CarScheduleDTO>> atomicReference= new AtomicReference<>();

        carScheduleRepository.findById(id).ifPresentOrElse(foundSchedule -> {
            CarSchedule carSchedule = carScheduleMapper.carScheduleDtoToCarSchedule(carScheduleDTO);
            foundSchedule.setCar(carSchedule.getCar());
            foundSchedule.setStartDate(carSchedule.getStartDate());
            foundSchedule.setEndDate(carSchedule.getEndDate());
            foundSchedule.setStatus(foundSchedule.getStatus());

            atomicReference.set(Optional.of(carScheduleMapper
                    .carScheduleToCarScheduleDTO(carScheduleRepository.save(foundSchedule))));
            loggingService.logInfo("Schedule updated successfully");
        }, () -> {
                    atomicReference.set(Optional.empty());
                    loggingService.logInfo("Schedule not found for ID: "+id);
                }
        );


        return atomicReference.get();
    }

    @Override
    public CarScheduleDTO createCarScheduleForCarOrder(OrderCreationRequest orderRequest) {

        CarScheduleDTO schedule = CarScheduleDTO.builder()
                        .car(carService.getCarFullDetailsById(orderRequest.getCarId()).orElseThrow(NotFoundException::new))
                        .status(ScheduleStatus.BOOKED)
                        .startDate(orderRequest.getStartDate())
                        .endDate(orderRequest.getEndDate()).build();


        return carScheduleMapper.carScheduleToCarScheduleDTO(carScheduleRepository
                .save(carScheduleMapper.carScheduleDtoToCarSchedule(schedule)));
    }

    @Override
    public boolean isCarBooked(CarScheduleDTO carSchedule, UUID excludeScheduleId) {
        loggingService.logInfo("Checking if car("
                + carSchedule.getCar().getId()
                +") is booked for period: "
                + carSchedule.getStartDate()
                + " - " + carSchedule.getEndDate());

        return carScheduleRepository.isCarBooked(
                carSchedule.getCar().getId(),
                carSchedule.getStartDate(),
                carSchedule.getEndDate(),
                excludeScheduleId);
    }
}
