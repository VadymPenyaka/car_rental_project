package nulp.cs.carrentalrestservice.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.event.CreateMaintenanceEvent;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.model.enumeration.ScheduleStatus;
import nulp.cs.carrentalrestservice.service.CarMaintenanceService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MaintenanceListener {
    private final CarMaintenanceService carMaintenanceService;

    @EventListener
    public void handleMaintenanceEvent (CreateMaintenanceEvent event) {
        CarOrderDTO carOrderDTO = event.getCarOrder();
        CarScheduleDTO carScheduleDTO = CarScheduleDTO.builder()
                .status(ScheduleStatus.UNDER_SERVICE)
                .car(carOrderDTO.getSchedule().getCar())
                .startDate(carOrderDTO.getSchedule().getEndDate().plusDays(1))
                .endDate(carOrderDTO.getSchedule().getEndDate().plusDays(2))
                .build();

        CarMaintenanceDTO carMaintenanceDTO = CarMaintenanceDTO.builder()
                .price(100.0)
                .schedule(carScheduleDTO)
                .description("Cleaning")
                .build();

        carMaintenanceService.createCarMaintenance(carMaintenanceDTO);
    }
}
