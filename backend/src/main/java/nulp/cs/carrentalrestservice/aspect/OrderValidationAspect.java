package nulp.cs.carrentalrestservice.aspect;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.CarUnavailableException;
import nulp.cs.carrentalrestservice.exception.CategoryExperienceVerificationException;
import nulp.cs.carrentalrestservice.exception.CategoryVerificationException;
import nulp.cs.carrentalrestservice.exception.InvalidOrderException;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.security.PersonDetails;
import nulp.cs.carrentalrestservice.service.CarOrderService;
import nulp.cs.carrentalrestservice.service.CarService;
import nulp.cs.carrentalrestservice.service.CustomerService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderValidationAspect {
    private final CustomerService customerService;
    private final CarOrderService carOrderService;
    private final CarService carService;

    @Before(value = "@annotation(nulp.cs.carrentalrestservice.annotation.VerifyOrder) && args(orderRequest, personDetails)", argNames = "orderRequest,personDetails")
    public void checkOrderAvailability(OrderCreationRequest orderRequest, PersonDetails personDetails) {
        if(!customerService.verifyCustomerForOrder(orderRequest)) {
            throw new CategoryExperienceVerificationException("You have not necessary amount of experience.");
        }

        if (!carService.verifyCarForOrder(orderRequest)) {
            throw new CarUnavailableException("Car is booked for this period");
        }

        if (carOrderService.isCustomerHasOverlapOrder(customerService.getAuthenticatedCustomer().getId(), orderRequest.getStartDate(), orderRequest.getEndDate()))
            throw new InvalidOrderException("You have another order for this period.");

    }
}
