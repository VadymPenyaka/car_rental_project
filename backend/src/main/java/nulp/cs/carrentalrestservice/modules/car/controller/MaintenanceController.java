package nulp.cs.carrentalrestservice.modules.car.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.modules.car.dto.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.modules.car.serivce.CarMaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(MaintenanceController.BASE_PATH)
public class MaintenanceController {
    private final CarMaintenanceService carMaintenanceService;
    public static final String BASE_PATH = "/admin/maintenance";

    @GetMapping("/{id}")
    public CarMaintenanceDTO getMaintenanceById(@PathVariable("id") UUID id) {
        return carMaintenanceService.getCarMaintenanceById(id)
                .orElseThrow(NotFoundException::new);
    }


    @PostMapping
    public ResponseEntity<?> createMaintenance(@RequestBody CarMaintenanceDTO carMaintenanceDTO) {
        carMaintenanceService.createCarMaintenance(carMaintenanceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaintenanceById(@PathVariable("id") UUID id, @RequestBody CarMaintenanceDTO carMaintenanceDTO) {
        if(carMaintenanceService.updateCarMaintenanceById(id, carMaintenanceDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaintenanceById(@PathVariable("id") UUID id) {
        if(!carMaintenanceService.deleteCarMaintenanceById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
