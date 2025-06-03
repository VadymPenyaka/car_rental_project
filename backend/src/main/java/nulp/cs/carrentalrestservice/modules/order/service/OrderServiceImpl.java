package nulp.cs.carrentalrestservice.modules.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.OrderStatus;
import nulp.cs.carrentalrestservice.modules.order.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.modules.order.repository.OrderRepository;
import nulp.cs.carrentalrestservice.shared.annotation.VerifyOrder;
import nulp.cs.carrentalrestservice.shared.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.shared.event.OrderDocumentEvent;
import nulp.cs.carrentalrestservice.shared.exception.InvalidOrderException;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.modules.car.CarScheduleDTO;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.modules.car.serivce.ScheduleService;
import nulp.cs.carrentalrestservice.modules.bankid.serivce.BankIdVerificationService;
import nulp.cs.carrentalrestservice.modules.admin.service.AdminService;
import nulp.cs.carrentalrestservice.modules.car.serivce.CarPricingService;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CarOrderMapper carOrderMapper;
    private final AdminService adminService;
    private final ScheduleService scheduleService;
    private final BankIdVerificationService customerInfoService;
    private final CarPricingService pricingService;
    private final ApplicationEventPublisher publisher;
    private final LoggingService loggingService;


    /**
     * Creates a new car order.
     * @param orderRequest the request that contains customer id, car id, start date, end date, and total price
     * @throws InvalidOrderException if the customer has another order for this period
     */
//    TODO add method to find admin+, calculate price+, aspect to bank, send pay link to customer
//    TODO create method to get authenticated customer+, create method to get schedule with car+,
    @Override
    @VerifyOrder
    @Transactional
    public void createCarOrder(OrderCreationRequest orderRequest) {
        PersonalDataDTO personalData = customerInfoService.getAuthenticatedCustomerInfo()
                .orElseThrow(()->new NotFoundException("Personal data not found."));

        if (isCustomerHasOverlapOrder(personalData.getId(), orderRequest.getStartDate(), orderRequest.getEndDate())) {
            throw new InvalidOrderException("You have another order for this period.");
        }
        CarScheduleDTO schedule = scheduleService.createCarScheduleForCarOrder(orderRequest);

        CarOrderDTO carOrderDTO = CarOrderDTO.builder()
                .totalPrice(pricingService.calculateOrderPrice(orderRequest))
                .status(OrderStatus.PENDING)
                .schedule(schedule)
                .admin(adminService.getAdminForOrderByLocation(schedule.getCar().getLocation()))
                .person(personalData.getPerson())
                .build();

        // Publish an event to create a maintenance for the car
        publisher.publishEvent(new CreateMaintenanceEvent(this, carOrderDTO));

        // Save the order to the database
        CarOrderDTO savedOrder = carOrderMapper.carOrderToCarOrderDto(orderRepository
                .save(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO)));

        // Publish an event to create a document for the order
        publisher.publishEvent(new OrderDocumentEvent(this, savedOrder.getId()));

        loggingService.logInfo("Car order created successfully");
    }


    @Override
    public Optional<CarOrderDTO> getCarOrderByID(UUID id) {
        loggingService.logInfo("Updating car order by ID: " + id);
        Optional<CarOrderDTO> carOrderDTO = Optional.ofNullable(carOrderMapper.carOrderToCarOrderDto(orderRepository
                .findById(id).orElse(null)));

        if (carOrderDTO.isPresent()) {
//            carOrderDTO.get().setPersonalData(customerInfoService.getAuthenticatedCustomerInfo());
        } else {
            loggingService.logDebug("Car order not found for ID: " + id);
        }


        return carOrderDTO;
    }

    //TODO ???
    @Override
    public Optional<CarOrderDTO> updateCarOrderById(UUID id, CarOrderDTO carOrderDTO) {
//        loggingService.logInfo("Updating car order with ID: " + id);
//        AtomicReference<Optional<CarOrderDTO>> atomicReference = new AtomicReference<>();
//
//        orderRepository.findById(id).ifPresentOrElse(foundOrder -> {
//            foundOrder.setStatus(carOrderDTO.getStatus());
//            publisher.publishEvent(new OrderEmailEvent(this, carOrderDTO, carOrderDTO.getPersonalData()));
//
//            CarOrderDTO updatedOrder = carOrderMapper.carOrderToCarOrderDto(orderRepository.save(foundOrder));
//            atomicReference.set(Optional.of(updatedOrder));
//
//            loggingService.logInfo("Car order updated successfully");
//        }, () -> {
//            loggingService.logDebug("Car order not found for ID: " + id);
//            atomicReference.set(Optional.empty());
//        });
//
//        return atomicReference.get();
        return null;
    }

    @Override
    public boolean isOwner(UUID orderId, String username) {
        loggingService.logInfo("Checking ownership for order ID: " + orderId + " and username: " + username);
        boolean isOwner = orderRepository.findById(orderId)
                .map(order -> order.getPerson().getUsername().equals(username))
                .orElse(false);

        loggingService.logInfo("Ownership check result: " + isOwner);
        return isOwner;
    }

    @Override
    public boolean isCustomerHasOverlapOrder(UUID personId, LocalDate startDate, LocalDate endDate) {
        return orderRepository.isCustomerHasOverlapOrder(personId, startDate, endDate);
    }

// TODO add method to controller
    @Override
    public List<CarOrderDTO> getOrdersByPersonId(UUID personId, OrderStatus orderStatus) {
        return orderRepository.findAllByPerson_IdAndStatus(personId, orderStatus)
                .stream().map(carOrderMapper::carOrderToCarOrderDto).toList();
    }

}
