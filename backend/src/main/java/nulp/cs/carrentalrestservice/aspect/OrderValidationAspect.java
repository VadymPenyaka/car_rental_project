package nulp.cs.carrentalrestservice.aspect;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.CarUnavailableException;
import nulp.cs.carrentalrestservice.exception.CategoryExperienceVerificationException;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.dto.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.ScheduleStatus;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.service.car.CarScheduleService;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.service.person.customer.CustomerService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderValidationAspect {
    private final CustomerService customerService;
    private final CarService carService;
    private final CarScheduleService scheduleService;

    /**
     * Validate an order creation request.
     * <p>
     * This aspect method is called before the annotated method with the given
     * argument.
     * <p>
     * The method checks if the customer has enough experience for the car category
     * and if the car is available for the given period.
     * <p>
     * If any of the checks fail, a corresponding exception is thrown.
     * <p>
     * The annotated method is not called if this method throws an exception.
     *
     * @param orderRequest the order creation request to validate
     */
    @Before(value = "@annotation(nulp.cs.carrentalrestservice.annotation.VerifyOrder) && args(orderRequest)", argNames = "orderRequest")
    public void validateOrder(OrderCreationRequest orderRequest) {
        if(!customerService.verifyCustomerForOrder(orderRequest)) {
            throw new CategoryExperienceVerificationException("You have not necessary amount of experience.");
        }

        CarDTO carDTO = carService.getCarFullDetailsById(orderRequest.getCarId())
                .orElseThrow(() -> new NotFoundException("Car not found"));

        CarScheduleDTO scheduleDTO = CarScheduleDTO.builder()
                .car(carDTO)
                .startDate(orderRequest.getStartDate())
                .endDate(orderRequest.getEndDate())
                .status(ScheduleStatus.BOOKED)
                .build();

        if (scheduleService.isCarBooked(scheduleDTO, null)) {
            throw new CarUnavailableException("Car is booked for this period");
        }
    }
}
