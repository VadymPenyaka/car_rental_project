package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.service.order.OrderService;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    public final static String BASE_PATH = "/api/v1/carOrders";
//TODO pre authorize dont work
    @PostMapping(BASE_PATH)
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createCarOrder (@RequestBody OrderCreationRequest orderRequest) {
        orderService.createCarOrder(orderRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasRole('ADMIN') AND @orderServiceImpl.isOwner(#id, #principal.username)")
    public CarOrderDTO getCarOrderById (@PathVariable("id") UUID id) {
        return orderService.getCarOrderByID(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCarOrderByID (@PathVariable("id") UUID id, @RequestBody CarOrderDTO carOrderDTO) {
        if (orderService.updateCarOrderById(id, carOrderDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
