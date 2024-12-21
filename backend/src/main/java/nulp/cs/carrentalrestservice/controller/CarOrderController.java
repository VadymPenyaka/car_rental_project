package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.security.CarOrderPermissionEvaluator;
import nulp.cs.carrentalrestservice.service.CarOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CarOrderController {
    private final CarOrderService carOrderService;
    public final static String BASE_PATH = "/api/v1/carOrders";

    @PostMapping(BASE_PATH)
    @PreAuthorize("hasRole(T(nulp.cs.carrentalrestservice.model.enumeration.Role).USER.name())")
    public ResponseEntity createCarOrder (@RequestBody CarOrderDTO carOrderDTO) {
        carOrderService.createCarOrder(carOrderDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(T(nulp.cs.carrentalrestservice.model.enumeration.Role).ADMIN.name()) or" +
            "@carOrderPermissionEvaluator.isOwner(#id, principal)")
    @GetMapping(BASE_PATH +"/{id}")
    public CarOrderDTO getCarOrderById (@PathVariable("id") UUID id) {
        return carOrderService.getCarOrderByID(id).orElseThrow(NotFoundException::new);
    }

    @PutMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasRole(T(nulp.cs.carrentalrestservice.model.enumeration.Role).ADMIN.name())")
    public ResponseEntity updateCarOrderByID (@PathVariable("id") UUID id, @RequestBody CarOrderDTO carOrderDTO) {
        if (carOrderService.updateCarOrderById(id, carOrderDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
