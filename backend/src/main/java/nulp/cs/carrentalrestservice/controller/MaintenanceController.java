package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarMaintenanceDTO;
import nulp.cs.carrentalrestservice.service.CarMaintenanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MaintenanceController {
    private final CarMaintenanceService carMaintenanceService;
    private final String BASE_PATH = "api/v1/maintenance";

    @GetMapping(BASE_PATH+"/{id}")
    public CarMaintenanceDTO getMaintenanceById(@PathVariable("id") Long id) {
        return carMaintenanceService.getCarMaintenanceById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(BASE_PATH)
    public ResponseEntity createMaintenance(@RequestBody CarMaintenanceDTO carMaintenanceDTO) {
        carMaintenanceService.createCarMaintenance(carMaintenanceDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(BASE_PATH+"/{id}")
    public ResponseEntity updateMaintenanceById(@PathVariable("id") Long id, @RequestBody CarMaintenanceDTO carMaintenanceDTO) {
        if(carMaintenanceService.updateCarMaintenanceById(id, carMaintenanceDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_PATH+"/{id}")
    public ResponseEntity deleteMaintenanceById(@PathVariable("id") Long id) {
        if(!carMaintenanceService.deleteCarMaintenanceById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
