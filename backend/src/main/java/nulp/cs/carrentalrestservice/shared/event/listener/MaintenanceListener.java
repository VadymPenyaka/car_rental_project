package nulp.cs.carrentalrestservice.shared.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.shared.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.modules.car.dto.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.modules.car.CarScheduleDTO;
import nulp.cs.carrentalrestservice.modules.car.dto.ScheduleStatus;
import nulp.cs.carrentalrestservice.modules.car.serivce.CarMaintenanceService;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaintenanceListener {
    private final CarMaintenanceService carMaintenanceService;
    private final LoggingService loggingService;

    @EventListener
    public void handleMaintenanceEvent (CreateMaintenanceEvent event) {
        loggingService.logDebug("handleMaintenanceEvent 1");
        CarOrderDTO carOrderDTO = event.getCarOrder();
        CarScheduleDTO carScheduleDTO = CarScheduleDTO.builder()
                .status(ScheduleStatus.UNDER_SERVICE)
                .car(carOrderDTO.getSchedule().getCar())
                .startDate(carOrderDTO.getSchedule().getEndDate().plusDays(1))
                .endDate(carOrderDTO.getSchedule().getEndDate().plusDays(2))
                .build();
        loggingService.logDebug("handleMaintenanceEvent 2");
        CarMaintenanceDTO carMaintenanceDTO = CarMaintenanceDTO.builder()
                .price(100.0)
                .schedule(carScheduleDTO)
                .description("Cleaning")
                .build();

        loggingService.logDebug("handleMaintenanceEvent 3");
        carMaintenanceService.createCarMaintenance(carMaintenanceDTO);
    }
}
