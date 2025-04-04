package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.service.car.CarPricingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller

@RequiredArgsConstructor
public class CarPricingController {
    private final CarPricingService carPricingService;
    public final static String BASE_PATH = "/api/v1/carPricing";

    @GetMapping(BASE_PATH +"/{id}")
    public CarPricingDTO getCarPricingById (@PathVariable UUID id) {
        return carPricingService.getCarPricingById(id)
                .orElseThrow(NotFoundException::new);
    }


    @GetMapping(BASE_PATH +"/getByCarId" +"/{carId}")
    public CarPricingDTO getCarPricingByCarId (@PathVariable UUID carId) {
        return carPricingService.getCarPricingByCarId(carId)
                .orElseThrow(NotFoundException::new);
    }


}
