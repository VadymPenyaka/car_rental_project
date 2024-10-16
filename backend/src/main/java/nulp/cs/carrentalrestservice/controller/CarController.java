package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;
import nulp.cs.carrentalrestservice.service.CarService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarController {
    public static final String BASE_PATH = "/api/v1/cars";

    private final CarService carService;


    @GetMapping(BASE_PATH)
    public List<CarDTO> getAllCarsByCriteria(@RequestParam(required = false) UUID locationId,
                                             @RequestParam(required = false) CarClass carClass,
                                             @RequestParam(required = false) String brand,
                                             @RequestParam(required = false) GearboxType gearboxType,
                                             @RequestParam(required = false) FuelType fuelType,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return carService.getAllCarsByCriteria(locationId, carClass, brand, gearboxType, fuelType, startDate, endDate);
    }

    @PostMapping(BASE_PATH)
    public ResponseEntity createCar (@RequestBody CarDTO car) {
        carService.createCar(car);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    public CarDTO getCarByID (@PathVariable("id") UUID id) {

        return carService.getCarByID(id).orElseThrow(NotFoundException::new);
    }


    @PutMapping(BASE_PATH+"/{id}")
    public ResponseEntity updateCarById (@PathVariable UUID id, @RequestBody CarDTO car) {
        if(carService.updateCarByID(id, car).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_PATH+"/{id}")
    public ResponseEntity deleteCarById (@PathVariable UUID id) {
        if (!carService.deleteCarById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
