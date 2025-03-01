package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.service.CarService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(CarAdminController.BASE_PATH)
public class CarAdminController {
    private final CarService carService;
    private final LoggingService loggingService;
    public static final String BASE_PATH = "/admin/cars";


    @PostMapping
    public ResponseEntity<?> createCar (@RequestBody CarDTO car) {
        carService.createCar(car);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarById (@PathVariable UUID id, @RequestBody CarDTO car) {
        loggingService.logInfo("Update car with id:"+car.getId()+";");
        if(carService.updateCarByID(id, car).isEmpty()) {
            loggingService.logError("Car not found;", new NotFoundException());
            throw new NotFoundException();
        }

        loggingService.logInfo("Car " + car.getId() + " wos updated");

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarById (@PathVariable UUID id) {
        if (!carService.deleteCarById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
