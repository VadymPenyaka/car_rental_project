package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.service.CarOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarOrderController {
    private final CarOrderService carOrderService;
    public final static String BASE_PATH = "/api/v1/carOrders";

    @PostMapping(BASE_PATH)
//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> createCarOrder (@RequestBody OrderCreationRequest orderRequest) {
        carOrderService.createCarOrder(orderRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //    TODO make check if user is the owner id order
    @GetMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasRole('ADMIN') ")
    public CarOrderDTO getCarOrderById (@PathVariable("id") UUID id) {
        return carOrderService.getCarOrderByID(id).orElseThrow(NotFoundException::new);
    }

//    TODO create function to get all orders of customer

    @PutMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCarOrderByID (@PathVariable("id") UUID id, @RequestBody CarOrderDTO carOrderDTO) {
        if (carOrderService.updateCarOrderById(id, carOrderDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
