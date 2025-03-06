package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
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

    @Override
    public Optional<CarScheduleDTO> getCarScheduleById(UUID id) {
        loggingService.logInfo("Getting car schedule for ID: " + id);
        return Optional.ofNullable(carScheduleMapper
                .carScheduleToCarScheduleDTO(carScheduleRepository.findById(id).get()));
    }

    @Override
    public Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, UUID id) {
        loggingService.logInfo("Updating car schedule for ID: " + id);
        if (checkIfCarBooked(carScheduleDTO, id)) {
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
    public CarScheduleDTO createCarSchedule(CarScheduleDTO carSchedule) {
        loggingService.logInfo(
                "Creating car schedule for car:"
                + carSchedule.getCar()
                + " ("+carSchedule.getStartDate()
                + "-"+carSchedule.getEndDate()+")");


        if (checkIfCarBooked(carSchedule, null)){
            throw new IllegalArgumentException("Car already booked for this period!");
        }

        return carScheduleMapper.carScheduleToCarScheduleDTO(carScheduleRepository
                .save(carScheduleMapper.carScheduleDtoToCarSchedule(carSchedule)));
    }

    @Override
    public boolean deleteCarScheduleById(UUID id) {
        loggingService.logInfo("Deleting car schedule for ID: " + id);
//TODO delete method or update to delete car order or maintenance with it
        if (carScheduleRepository.existsById(id)) {
            carScheduleRepository.deleteById(id);
            loggingService.logInfo("Schedule deleted successfully");
            return true;
        }
        loggingService.logInfo("Schedule not found for ID: " + id);
        return false;
    }

    @Override
    public boolean checkIfCarBooked(CarScheduleDTO carSchedule, UUID excludeScheduleId) {
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
