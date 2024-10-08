package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CarMaintenanceMapper;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.repository.CarMaintenanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarMaintenanceServiceImpl implements CarMaintenanceService {
    private final CarMaintenanceRepository carMaintenanceRepository;
    private final CarMaintenanceMapper carMaintenanceMapper;
    private final CarScheduleService carScheduleService;
    private final CarScheduleMapper carScheduleMapper;

    @Override
    public Optional<CarMaintenanceDTO> getCarMaintenanceById(Long id) {
        return Optional.ofNullable(carMaintenanceMapper
                .carMaintenanceToCarMaintenanceDTO(carMaintenanceRepository.findById(id).get()));
    }

    @Override
    public CarMaintenanceDTO createCarMaintenance(CarMaintenanceDTO carMaintenanceDTO) {
        carMaintenanceDTO.setSchedule(carScheduleService
                .createCarSchedule(carMaintenanceDTO.getSchedule()));

        return carMaintenanceMapper.carMaintenanceToCarMaintenanceDTO(carMaintenanceRepository
                .save(carMaintenanceMapper.carMaintenanceDTOToCarMaintenance(carMaintenanceDTO)));
    }

    @Override
    public Optional<CarMaintenanceDTO> updateCarMaintenanceById(Long id, CarMaintenanceDTO carMaintenanceDTO) {
        AtomicReference<Optional<CarMaintenanceDTO>> carMaintenanceRef = new AtomicReference<>();

        carMaintenanceRepository.findById(id).ifPresentOrElse( foundMaintenance -> {
                    CarScheduleDTO updatedScheduleDTO = carMaintenanceDTO.getSchedule();
                    foundMaintenance.setSchedule(carScheduleMapper.carScheduleDtoToCarSchedule(carScheduleService
                            .updateCarScheduleById(updatedScheduleDTO, updatedScheduleDTO.getId()).get()));

                    foundMaintenance.setDescription(carMaintenanceDTO.getDescription());
                    foundMaintenance.setPrice(carMaintenanceDTO.getPrice());

                    carMaintenanceRef.set(Optional.of(carMaintenanceMapper
                            .carMaintenanceToCarMaintenanceDTO(carMaintenanceRepository.save(foundMaintenance))));
                }, () -> carMaintenanceRef.set(Optional.empty())
        );

        return carMaintenanceRef.get();
    }

    @Override
    public boolean deleteCarMaintenanceById(Long id) {
        if(carMaintenanceRepository.existsById(id)) {
            carMaintenanceRepository.deleteById(id);
            carScheduleService.deleteCarScheduleById(id);
            return true;
        }

        return false;
    }
}
