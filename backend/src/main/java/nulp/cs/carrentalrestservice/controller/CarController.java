package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
import nulp.cs.carrentalrestservice.model.response.CategoryPriceRangeResponse;
import nulp.cs.carrentalrestservice.service.car.CarService;
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
    public List<CarCardResponse> getAllCarsByCriteria(@Valid @RequestBody(required = false) CarSearchRequest searchRequest) {
        if (searchRequest==null) {
            carService.getAll();
        }
        return carService.getAllCarsByCriteria(searchRequest);
    }

    @GetMapping("/{id}")
    public CarCustomerDetailsResponse getCarById (@PathVariable UUID id) {
        return carService.getCarCustomerDetailsById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping("/categoriesPriceRange")
    public List<CategoryPriceRangeResponse> getCategoriesPriceRange () {
        return carService.getCarCategoriesPriceRanges();
    }

}
