package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarOrderServiceImpl implements CarOrderService {
    private final CarOrderRepository carOrderRepository;
    private final CarOrderMapper carOrderMapper;
    private final CarScheduleService carScheduleService;
    private final CarScheduleMapper carScheduleMapper;

    private final ApplicationEventPublisher publisher;

    @Override
    public CarOrderDTO createCarOrder(CarOrderDTO carOrderDTO) {
        validateOrder(carOrderDTO);
        carOrderDTO.setSchedule(carScheduleService.createCarSchedule(carOrderDTO.getSchedule()));

        publisher.publishEvent(new CreateMaintenanceEvent(this, carOrderDTO));
        return carOrderMapper.carOrderToCarOrderDto(carOrderRepository
                .save(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO)));
    }

    @Override
    public Optional<CarOrderDTO> getCarOrderByID(UUID id) {
        return Optional.ofNullable(carOrderMapper.carOrderToCarOrderDto(carOrderRepository
                .findById(id).orElse(null)));
    }

    @Override
    public boolean deleteCarOrderById(UUID id) {
        if (carOrderRepository.existsById(id)) {
            carOrderRepository.deleteById(id);
//            carScheduleService.deleteCarScheduleById(id);
            //TODO
            return true;
        }
        return false;
    }

    @Override
    public Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO) {
        AtomicReference<Optional<CarOrderDTO>> atomicReference = new AtomicReference<>();

        carOrderRepository.findById(id).ifPresentOrElse(foundOrder -> {
            foundOrder.setStatus(carOrderDTO.getStatus());
            publisher.publishEvent(new EmailEvent(this, carOrderDTO, carOrderDTO.getCustomer()));

            CarScheduleDTO scheduleDTO = carOrderDTO.getSchedule();
            foundOrder.setSchedule(carScheduleMapper.carScheduleDtoToCarSchedule(carScheduleService//update car schedule
                    .updateCarScheduleById(scheduleDTO, scheduleDTO.getId()).get()));
            atomicReference.set(Optional.of(carOrderMapper.carOrderToCarOrderDto(carOrderRepository.save(foundOrder))));
        }, ()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    public void validateOrder(CarOrderDTO carOrderDTO) {

        if (carOrderRepository.isCustomerHasOverlapOrder(carOrderDTO.getCustomer().getId(),
                carOrderDTO.getSchedule().getStartDate(),
                carOrderDTO.getSchedule().getEndDate()))
            throw new IllegalArgumentException("You have another order for this period.");
    }

}
