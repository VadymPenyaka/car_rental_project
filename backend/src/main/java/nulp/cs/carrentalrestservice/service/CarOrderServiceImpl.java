package nulp.cs.carrentalrestservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.CheckOrderAvailability;
import nulp.cs.carrentalrestservice.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.mapper.CarScheduleMapper;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
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
    private final LoggingService loggingService;

    @Override
    @CheckOrderAvailability
    public CarOrderDTO createCarOrder(CarOrderDTO carOrderDTO) {
        loggingService.logInfo("Creating car order for customer with email: " + carOrderDTO.getCustomer().getPerson().getUsername());

        carOrderDTO.setSchedule(carOrderDTO.getSchedule());

        publisher.publishEvent(new CreateMaintenanceEvent(this, carOrderDTO));
        CarOrderDTO savedOrder = carOrderMapper.carOrderToCarOrderDto(carOrderRepository
                .save(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO)));

        loggingService.logInfo("Car order created successfully");
        return savedOrder;
    }

    @Override
    public Optional<CarOrderDTO> getCarOrderByID(UUID id) {
        loggingService.logInfo("Updating car order by ID: " + id);
        Optional<CarOrderDTO> carOrderDTO = Optional.ofNullable(carOrderMapper.carOrderToCarOrderDto(carOrderRepository
                .findById(id).orElse(null)));

        if (carOrderDTO.isPresent()) {
            loggingService.logInfo("Car order updated successfully for ID: " + carOrderDTO.get().getId());
        } else {
            loggingService.logDebug("Car order not found for ID: " + id);
        }

        return carOrderDTO;
    }

    @Override
    public Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO) {
        loggingService.logInfo("Updating car order with ID: " + id);
        AtomicReference<Optional<CarOrderDTO>> atomicReference = new AtomicReference<>();

        carOrderRepository.findById(id).ifPresentOrElse(foundOrder -> {
            foundOrder.setStatus(carOrderDTO.getStatus());
            publisher.publishEvent(new EmailEvent(this, carOrderDTO, carOrderDTO.getCustomer()));

            CarScheduleDTO scheduleDTO = carOrderDTO.getSchedule();
            foundOrder.setSchedule(carScheduleMapper.carScheduleDtoToCarSchedule(carScheduleService
                    .updateCarScheduleById(scheduleDTO, scheduleDTO.getId()).get()));
            CarOrderDTO updatedOrder = carOrderMapper.carOrderToCarOrderDto(carOrderRepository.save(foundOrder));
            atomicReference.set(Optional.of(updatedOrder));

            loggingService.logInfo("Car order updated successfully");
        }, () -> {
            loggingService.logDebug("Car order not found for ID: " + id);
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    //todo make work with person
    @Override
    public boolean isOwner(UUID orderId, String username) {
        loggingService.logInfo("Checking ownership for order ID: " + orderId + " and username: " + username);
//        boolean isOwner = carOrderRepository.findById(orderId)
//                .map(order -> order.getCustomer().getEmail().equals(username))
//                .orElse(false);

//        loggingService.logInfo("Ownership check result: " + isOwner);
//        return isOwner;
        return true;
    }
}
