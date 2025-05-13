package nulp.cs.carrentalrestservice.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.model.dto.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.ScheduleStatus;
import nulp.cs.carrentalrestservice.service.car.CarMaintenanceService;
import nulp.cs.carrentalrestservice.util.logging.LoggingService;
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
