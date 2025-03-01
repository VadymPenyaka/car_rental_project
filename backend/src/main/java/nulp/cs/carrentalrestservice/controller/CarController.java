package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.enumeration.FuelType;
import nulp.cs.carrentalrestservice.model.enumeration.GearboxType;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequestDto;
import nulp.cs.carrentalrestservice.model.response.CarCardDTO;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsDTO;
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

    @PostMapping(BASE_PATH)
    public List<CarCardDTO> getAllCarsByCriteria(@RequestBody CarSearchRequestDto carDTO) {
        return carService.getAllCarsByCriteria(carDTO);
    }

    @GetMapping(BASE_PATH+"/{id}")
    public CarCustomerDetailsDTO getCarById (@PathVariable UUID id) {
        return carService.getCarCustomerDetailsById(id).orElseThrow(NotFoundException::new);
    }


}
