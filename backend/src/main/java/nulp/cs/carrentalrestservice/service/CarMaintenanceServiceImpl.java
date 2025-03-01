package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CarMaintenanceMapper;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.repository.CarMaintenanceRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarMaintenanceServiceImpl implements CarMaintenanceService {
    private final CarMaintenanceRepository carMaintenanceRepository;
    private final CarMaintenanceMapper carMaintenanceMapper;
    private final CarScheduleService carScheduleService;
    private final CarScheduleMapper carScheduleMapper;
    private final LoggingService loggingService;

    @Override
    public Optional<CarMaintenanceDTO> getCarMaintenanceById(UUID id) {
        loggingService.logInfo("Getting maintenance for  ID: " + id);
        Optional<CarMaintenanceDTO> result = carMaintenanceRepository.findById(id)
                .map(carMaintenanceMapper::carMaintenanceToCarMaintenanceDTO);

        if (result.isPresent()) {
            loggingService.logInfo("Maintenance found successfully");
        } else {
            loggingService.logInfo("Maintenance not found for ID: " + id);
        }

        return result;
    }

    @Override
    public CarMaintenanceDTO createCarMaintenance(CarMaintenanceDTO carMaintenanceDTO) {
        loggingService.logInfo("Creating maintenance: " + carMaintenanceDTO.getDescription());

        carScheduleService.createCarSchedule(carMaintenanceDTO.getSchedule());
        CarMaintenanceDTO savedMaintenance = carMaintenanceMapper.carMaintenanceToCarMaintenanceDTO(
                carMaintenanceRepository.save(
                        carMaintenanceMapper.carMaintenanceDTOToCarMaintenance(carMaintenanceDTO))
        );

        loggingService.logInfo("Maintenance created successfully");
        return savedMaintenance;
    }

    @Override
    public Optional<CarMaintenanceDTO> updateCarMaintenanceById(UUID id, CarMaintenanceDTO carMaintenanceDTO) {
        loggingService.logInfo("Updating maintenance for ID: " + id);

        AtomicReference<Optional<CarMaintenanceDTO>> carMaintenanceRef = new AtomicReference<>();

        carMaintenanceRepository.findById(id).ifPresentOrElse(foundMaintenance -> {
            CarScheduleDTO updatedScheduleDTO = carMaintenanceDTO.getSchedule();
            foundMaintenance.setSchedule(carScheduleMapper.carScheduleDtoToCarSchedule(
                    carScheduleService.updateCarScheduleById(updatedScheduleDTO, updatedScheduleDTO.getId()).get()));

            foundMaintenance.setDescription(carMaintenanceDTO.getDescription());
            foundMaintenance.setPrice(carMaintenanceDTO.getPrice());

            carMaintenanceRef.set(Optional.of(carMaintenanceMapper
                    .carMaintenanceToCarMaintenanceDTO(carMaintenanceRepository.save(foundMaintenance))));

            loggingService.logInfo("Maintenance updated successfully");
        }, () -> {
            loggingService.logInfo("Maintenance not found for ID: " + id);
            carMaintenanceRef.set(Optional.empty());
        });

        return carMaintenanceRef.get();
    }

    @Override
    public boolean deleteCarMaintenanceById(UUID id) {
        loggingService.logInfo("Deleting maintenance for ID: " + id);

        if (carMaintenanceRepository.existsById(id)) {
            carMaintenanceRepository.deleteById(id);
            loggingService.logInfo("Maintenance deleted successfully");
            return true;
        }

        loggingService.logInfo("Maintenance not found for ID: " + id);
        return false;
    }
}
