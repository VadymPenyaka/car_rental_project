package nulp.cs.carrentalrestservice.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import nulp.cs.carrentalrestservice.validation.CarValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(CarAdminController.BASE_PATH)
public class CarAdminController {
    private final CarService carService;
    private final ObjectMapper objectMapper;
    private final CarValidator carValidator;
    private final LoggingService loggingService;
    public static final String BASE_PATH = "/admin/cars";


    @PostMapping
    public ResponseEntity<?> createCar (@RequestHeader("car") @Valid String car, @RequestParam MultipartFile[] multipartFiles) {
        try {
            CarDTO carDTO = objectMapper.readValue(car, CarDTO.class);

            BeanPropertyBindingResult errors = new BeanPropertyBindingResult(carDTO, "carDTO");
            carValidator.validate(carDTO, errors);

            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().body(errors.getAllErrors());
            }

            carService.createCar(carDTO, multipartFiles);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating car: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public CarDTO getCarFulInfoById (@PathVariable("id") UUID id) {
        return carService.getCarFullDetailsById(id)
                .orElseThrow(() -> new NotFoundException("Car not found"));
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
