package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarScheduleServiceImpl implements CarScheduleService {
    private final CarScheduleRepository carScheduleRepository;
    private final CarScheduleMapper carScheduleMapper;

    @Override
    public Optional<CarScheduleDTO> getCarScheduleById(Long id) {
        return Optional.ofNullable(carScheduleMapper
                .carScheduleToCarScheduleDTO(carScheduleRepository.findById(id).get()));
    }

    @Override
    public Optional<CarScheduleDTO> updateCarScheduleById(CarScheduleDTO carScheduleDTO, Long id) {
        checkIfCarBooked(id, carScheduleDTO);

        AtomicReference<Optional<CarScheduleDTO>> atomicReference= new AtomicReference<>();

        carScheduleRepository.findById(id).ifPresentOrElse(foundSchedule -> {
            CarSchedule carSchedule = carScheduleMapper.carScheduleDtoToCarSchedule(carScheduleDTO);
            foundSchedule.setCar(carSchedule.getCar());
            foundSchedule.setStartDate(carSchedule.getStartDate());
            foundSchedule.setEndDate(carSchedule.getEndDate());
            foundSchedule.setStatus(foundSchedule.getStatus());

            atomicReference.set(Optional.of(carScheduleMapper
                    .carScheduleToCarScheduleDTO(carScheduleRepository.save(foundSchedule))));

        }, () -> atomicReference.set(Optional.empty()));


        return atomicReference.get();
    }

    @Override
    public CarScheduleDTO createCarSchedule(CarScheduleDTO carSchedule) {
        checkIfCarBooked(null, carSchedule);

        return carScheduleMapper.carScheduleToCarScheduleDTO(carScheduleRepository
                .save(carScheduleMapper.carScheduleDtoToCarSchedule(carSchedule)));
    }

    @Override
    public boolean deleteCarScheduleById(Long id) {
        if (carScheduleRepository.existsById(id)) {
            carScheduleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void checkIfCarBooked(Long excludeScheduleId, CarScheduleDTO carSchedule) {
        boolean isCarBooked = carScheduleRepository.isCarBooked(
                carSchedule.getCar().getId(),
                carSchedule.getStartDate(),
                carSchedule.getEndDate(),
                excludeScheduleId);

        if (isCarBooked) {
            throw new IllegalArgumentException("Car already booked for this period!");
        }
    }
}
