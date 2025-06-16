package nulp.cs.carrentalrestservice.modules.order.aspect;


import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.serivce.BankIdVerificationService;
import nulp.cs.carrentalrestservice.modules.car.CarScheduleDTO;
import nulp.cs.carrentalrestservice.modules.car.dto.CarDTO;
import nulp.cs.carrentalrestservice.modules.car.dto.ScheduleStatus;
import nulp.cs.carrentalrestservice.modules.car.serivce.CarService;
import nulp.cs.carrentalrestservice.modules.car.serivce.ScheduleService;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.shared.exception.CarUnavailableException;
import nulp.cs.carrentalrestservice.shared.exception.CategoryExperienceVerificationException;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderValidationAspect {
    private final BankIdVerificationService customerInfoService;
    private final CarService carService;
    private final ScheduleService scheduleService;

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
    @Before(value = "@annotation(nulp.cs.carrentalrestservice.shared.annotation.VerifyOrder) && args(orderRequest)", argNames = "orderRequest")
    public void validateOrder(OrderCreationRequest orderRequest) {
        if(!customerInfoService.verifyCustomerForOrder(orderRequest)) {
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
