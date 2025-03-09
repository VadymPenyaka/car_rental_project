package nulp.cs.carrentalrestservice.aspect;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import nulp.cs.carrentalrestservice.security.PersonDetails;
import nulp.cs.carrentalrestservice.service.CarScheduleService;
import nulp.cs.carrentalrestservice.service.CarService;
import nulp.cs.carrentalrestservice.service.CustomerService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderValidationAspect {
    private final CustomerService customerService;
    private final CarService carService;

    @Before(value = "@annotation(nulp.cs.carrentalrestservice.annotation.VerifyOrder) && args(orderRequest, personDetails)", argNames = "orderRequest,personDetails")
    public void checkOrderAvailability(OrderCreationRequest orderRequest, PersonDetails personDetails) {
        if(!customerService.verifyCustomerForOrder(orderRequest)) {
            throw new IllegalArgumentException("You have not necessary amount of experience(in years)");
        }

        if (!carService.verifyCarForOrder(orderRequest)) {
            throw new IllegalArgumentException("Car is booked for this period");
        }

//        TODO refactor if statement or rebase it to order service create method to get oll orders of customer
//        if (carOrderRepository.isCustomerHasOverlapOrder(carOrderDTO.getCustomer().getId(),
//                carSchedule.getStartDate(),
//                carSchedule.getEndDate()))
//            throw new IllegalArgumentException("You have another order for this period.");

    }
}
