package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;
import nulp.cs.carrentalrestservice.service.CarService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarController {
    public static final String BASE_PATH = "/api/v1/cars";
    private final CarService carService;
    private final LoggingService loggingService;

    @GetMapping(BASE_PATH)
    public List<CarDTO> getAllCarsByCriteria(CarDTO carDTO, LocalDate startDate, LocalDate endDate) {
        return carService.getAllCarsByCriteria(carDTO, startDate, endDate);
    }

    @PostMapping(BASE_PATH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCar (@RequestBody CarDTO car) {
        carService.createCar(car);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(BASE_PATH+"/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCarById (@PathVariable UUID id, @RequestBody CarDTO car) {
        loggingService.logInfo("Update car with id:"+car.getId()+";");
        if(carService.updateCarByID(id, car).isEmpty()) {
            loggingService.logError("Car not found;", new NotFoundException());
            throw new NotFoundException();
        }

        loggingService.logInfo("Car " + car.getId() + " wos updated");

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(BASE_PATH+"/{id}")
    public ResponseEntity<?> deleteCarById (@PathVariable UUID id) {
        if (!carService.deleteCarById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
