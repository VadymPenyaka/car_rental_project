package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import nulp.cs.carrentalrestservice.service.CarOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarOrderController {
    private final CarOrderService carOrderService;
    private final static String CAR_ORDER_BASE_PATH = "api/v1/carOrders";

    @PostMapping(CAR_ORDER_BASE_PATH)
    public ResponseEntity createCarOrder (@RequestBody CarOrderDTO carOrderDTO) {
        carOrderService.createCarOrder(carOrderDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(CAR_ORDER_BASE_PATH +"/{id}")
    public CarOrderDTO getCarOrderById (@PathVariable Long id) {
        return carOrderService.getCarOrderByID(id).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping (CAR_ORDER_BASE_PATH +"/{id}")
    public ResponseEntity deleteCarOrderById (@PathVariable Long id) {
        if (!carOrderService.deleteCarOrderById(id))
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CAR_ORDER_BASE_PATH +"/{id}")
    public ResponseEntity updateCarOrderByID (@PathVariable Long id, @RequestBody CarOrderDTO carOrderDTO) {
        if (carOrderService.updateCarOrderById(id, carOrderDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = CAR_ORDER_BASE_PATH, method = RequestMethod.GET)//TODO refactor
    public List<CarOrderDTO> getAllCarOrdersByStatus (@RequestParam("orderStatus") OrderStatus orderStatus) {
        return carOrderService.getAllCarOrdersByStatus(orderStatus);
    }

}
