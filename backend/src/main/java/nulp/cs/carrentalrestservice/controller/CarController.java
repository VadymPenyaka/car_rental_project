package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.Exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CarController {
    public static final String BASE_PATH_V1 = "/api/v1/cars";
    public static final String BASE_PATH_V2 = "/api/v2/cars";

    private final CarService carService;


    @GetMapping(BASE_PATH_V1)
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }


    @PostMapping(BASE_PATH_V1)
    public ResponseEntity createCar (@RequestBody CarDTO car) {
        carService.createCar(car);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH_V1+"/{id}")
    public CarDTO getCarByID (@PathVariable("id") Long id) {

        return carService.getCarByID(id).orElseThrow(NotFoundException::new);
    }


    @PutMapping(BASE_PATH_V2+"/{id}")
    public ResponseEntity updateCarById (@PathVariable Long id, @RequestBody CarDTO car) {
        if(carService.updateCarByID(id, car).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_PATH_V2+"/{id}")
    public ResponseEntity deleteCarById (@PathVariable Long id) {
        if (!carService.deleteCarById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping(BASE_PATH_V2+"/byClass")
//    public List<CarDTO> getAllCarsByClass (@RequestParam CarClass carClass) {
//        return carService.getCarsByCarClass(carClass);
//    }

}
