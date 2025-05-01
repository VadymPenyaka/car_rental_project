package nulp.cs.carrentalrestservice.service.order;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.annotation.VerifyOrder;
import nulp.cs.carrentalrestservice.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.exception.InvalidOrderException;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.OrderStatus;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.repository.OrderRepository;
import nulp.cs.carrentalrestservice.service.customer.CustomerService;
import nulp.cs.carrentalrestservice.service.admin.AdminService;
import nulp.cs.carrentalrestservice.service.car.CarPricingService;
import nulp.cs.carrentalrestservice.service.car.CarScheduleService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CarOrderMapper carOrderMapper;
    private final AdminService adminService;
    private final CarScheduleService scheduleService;
    private final CustomerService customerService;
    private final CarPricingService pricingService;
    private final ApplicationEventPublisher publisher;
    private final LoggingService loggingService;


//    TODO add method to find admin+, calculate price+, aspect to bank, send pay link to customer
//    TODO create method to get authenticated customer+, create method to get schedule with car+,
    @Override
    @VerifyOrder
    public void createCarOrder(OrderCreationRequest orderRequest) {
        if (isCustomerHasOverlapOrder(customerService.getAuthenticatedCustomer().getId(), orderRequest.getStartDate(), orderRequest.getEndDate())) {
            throw new InvalidOrderException("You have another order for this period.");
        }
        CarScheduleDTO schedule = scheduleService.createCarScheduleForCarOrder(orderRequest);

        CarOrderDTO carOrderDTO = CarOrderDTO.builder()
                .totalPrice(pricingService.calculateOrderPrice(orderRequest))
                .status(OrderStatus.PENDING)
                .schedule(schedule)
                .admin(adminService.getAdminForOrderByLocation(schedule.getCar().getLocation()))
                .customer(customerService.getAuthenticatedCustomer())
                .build();

        publisher.publishEvent(new CreateMaintenanceEvent(this, carOrderDTO));
        CarOrderDTO savedOrder = carOrderMapper.carOrderToCarOrderDto(orderRepository
                .save(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO)));

        loggingService.logInfo("Car order created successfully");
    }

    @Override
    public Optional<CarOrderDTO> getCarOrderByID(UUID id) {
        loggingService.logInfo("Updating car order by ID: " + id);
        Optional<CarOrderDTO> carOrderDTO = Optional.ofNullable(carOrderMapper.carOrderToCarOrderDto(orderRepository
                .findById(id).orElse(null)));

        if (carOrderDTO.isPresent()) {
            loggingService.logInfo("Car order updated successfully for ID: " + carOrderDTO.get().getId());
        } else {
            loggingService.logDebug("Car order not found for ID: " + id);
        }

        return carOrderDTO;
    }

    //TODO ???
    @Override
    public Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO) {
        loggingService.logInfo("Updating car order with ID: " + id);
        AtomicReference<Optional<CarOrderDTO>> atomicReference = new AtomicReference<>();

        orderRepository.findById(id).ifPresentOrElse(foundOrder -> {
            foundOrder.setStatus(carOrderDTO.getStatus());
            publisher.publishEvent(new EmailEvent(this, carOrderDTO, carOrderDTO.getCustomer()));
            
            CarOrderDTO updatedOrder = carOrderMapper.carOrderToCarOrderDto(orderRepository.save(foundOrder));
            atomicReference.set(Optional.of(updatedOrder));

            loggingService.logInfo("Car order updated successfully");
        }, () -> {
            loggingService.logDebug("Car order not found for ID: " + id);
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }

    @Override
    public boolean isOwner(UUID orderId, String username) {
        loggingService.logInfo("Checking ownership for order ID: " + orderId + " and username: " + username);
        boolean isOwner = orderRepository.findById(orderId)
                .map(order -> order.getCustomer().getPerson().getUsername().equals(username))
                .orElse(false);

        loggingService.logInfo("Ownership check result: " + isOwner);
        return isOwner;
    }

    @Override
    public boolean isCustomerHasOverlapOrder(UUID customerId, LocalDate startDate, LocalDate endDate) {
        return orderRepository.isCustomerHasOverlapOrder(customerId, startDate, endDate);
    }

}
