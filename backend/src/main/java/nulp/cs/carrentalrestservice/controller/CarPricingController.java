package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import nulp.cs.carrentalrestservice.service.CarPricingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CarPricingController {
    private final CarPricingService carPricingService;
    private final static String CAR_PRICING_BASE_PATH = "api/v1/carPricing";

    @GetMapping(CAR_PRICING_BASE_PATH+"/{id}")
    public CarPricingDTO getCarPricingById (@PathVariable Long id) {
        return carPricingService.getCarPricingById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(CAR_PRICING_BASE_PATH)
    public ResponseEntity createCarPricing (@RequestBody CarPricingDTO carPricing) {
        carPricingService.createCarPricing(carPricing);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(CAR_PRICING_BASE_PATH+"/{id}")
    public ResponseEntity updateCarPricingById (@PathVariable Long id, @RequestBody CarPricingDTO carPricing) {
        if(carPricingService.updateCarPricingByID(id, carPricing).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CAR_PRICING_BASE_PATH+"/{id}")
    public ResponseEntity deleteCarPricingById (@PathVariable Long id) {
        if(!carPricingService.deleteCarPricingById(id))
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(CAR_PRICING_BASE_PATH +"/getByCarId" +"/{carId}")
    public CarPricingDTO getCarPricingByCarId (@PathVariable Long carId) {
        return carPricingService.getCarPricingByCarId(carId)
                .orElseThrow(NotFoundException::new);
    }


}
