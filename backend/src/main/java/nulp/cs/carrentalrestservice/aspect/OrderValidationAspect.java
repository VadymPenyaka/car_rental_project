package nulp.cs.carrentalrestservice.aspect;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.CarUnavailableException;
import nulp.cs.carrentalrestservice.exception.CategoryExperienceVerificationException;
import nulp.cs.carrentalrestservice.exception.InvalidOrderException;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.service.order.OrderService;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.service.customer.CustomerService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderValidationAspect {
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CarService carService;

    @Before(value = "@annotation(nulp.cs.carrentalrestservice.annotation.VerifyOrder) && args(orderRequest)", argNames = "orderRequest")
    public void checkOrderAvailability(OrderCreationRequest orderRequest) {
        if(!customerService.verifyCustomerForOrder(orderRequest)) {
            throw new CategoryExperienceVerificationException("You have not necessary amount of experience.");
        }

        if (!carService.verifyCarForOrder(orderRequest)) {
            throw new CarUnavailableException("Car is booked for this period");
        }
        //TODO move to service
        if (orderService.isCustomerHasOverlapOrder(customerService.getAuthenticatedCustomer().getId(), orderRequest.getStartDate(), orderRequest.getEndDate()))
            throw new InvalidOrderException("You have another order for this period.");

    }
}
