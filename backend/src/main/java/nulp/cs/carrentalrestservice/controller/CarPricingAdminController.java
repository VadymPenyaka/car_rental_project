package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import nulp.cs.carrentalrestservice.service.CarPricingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(CarPricingAdminController.BASE_PATH)
public class CarPricingAdminController {
    public final static String BASE_PATH = "admin/carPricing";
    private final CarPricingService carPricingService;

    @PostMapping
    public ResponseEntity<?> createCarPricing (@RequestBody CarPricingDTO carPricing) {
        carPricingService.createCarPricing(carPricing);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarPricingById (@PathVariable UUID id, @RequestBody CarPricingDTO carPricing) {
        if(carPricingService.updateCarPricingByID(id, carPricing).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarPricingById (@PathVariable UUID id) {
        if(!carPricingService.deleteCarPricingById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
