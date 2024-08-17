package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController {
    public static final String BASE_PATH = "/api/v1/cars";

    private final CarService carService;


    @GetMapping(BASE_PATH)
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }


    @PostMapping(BASE_PATH)
    public ResponseEntity createCar (@RequestBody CarDTO car) {
        carService.createCar(car);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    public CarDTO getCarByID (@PathVariable("id") Long id) {

        return carService.getCarByID(id).orElseThrow(NotFoundException::new);
    }


    @PutMapping(BASE_PATH+"/{id}")
    public ResponseEntity updateCarById (@PathVariable Long id, @RequestBody CarDTO car) {
        if(carService.updateCarByID(id, car).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_PATH+"/{id}")
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
