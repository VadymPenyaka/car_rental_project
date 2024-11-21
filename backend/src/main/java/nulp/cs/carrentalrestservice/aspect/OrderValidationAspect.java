package nulp.cs.carrentalrestservice.aspect;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.CarSchedule;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import nulp.cs.carrentalrestservice.repository.CarScheduleRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderValidationAspect {
    private final CarScheduleRepository carScheduleRepository;
    private final CarOrderRepository carOrderRepository;


    @Before(value = "@annotation(nulp.cs.carrentalrestservice.annotation.CheckOrderAvailability) && args(carOrderDTO)")
    public void checkOrderAvailability(CarOrder carOrderDTO) {
        CarSchedule carSchedule = carOrderDTO.getSchedule();
        boolean isAvailable = carScheduleRepository.isCarBooked(carSchedule.getCar().getId(), carSchedule.getStartDate(), carSchedule.getEndDate().plusDays(1), null);

        if(!isAvailable) {
            throw new IllegalArgumentException("The car is not available for the selected period.");
        }

        if (carOrderRepository.isCustomerHasOverlapOrder(carOrderDTO.getCustomer().getId(),
                carSchedule.getStartDate(),
                carSchedule.getEndDate()))
            throw new IllegalArgumentException("You have another order for this period.");
    }
}
