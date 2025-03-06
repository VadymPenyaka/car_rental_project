package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequestDto;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
import nulp.cs.carrentalrestservice.service.CarService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CarController.BASE_PATH)
@RequiredArgsConstructor
public class CarController {
    public static final String BASE_PATH = "/api/v1/cars";
    private final CarService carService;

    @PostMapping
    public List<CarCardResponse> getAllCarsByCriteria(@RequestBody CarSearchRequestDto carDTO) {
        return carService.getAllCarsByCriteria(carDTO);
    }

    @GetMapping("/{id}")
    public CarCustomerDetailsResponse getCarById (@PathVariable UUID id) {
        return carService.getCarCustomerDetailsById(id).orElseThrow(NotFoundException::new);
    }


}
