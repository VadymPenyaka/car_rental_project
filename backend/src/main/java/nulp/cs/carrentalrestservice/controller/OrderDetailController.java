package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.Exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderDetailDTO;
import nulp.cs.carrentalrestservice.service.OrderDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderDetailController {
    private final static String ORDER_DETAIL_BASE_PATH_V1 = "api/v1/orderDetails";
    private final static String ORDER_DETAIL_BASE_PATH_V2 = "api/v2/orderDetails";

    private final OrderDetailService orderDetailService;

    @GetMapping(ORDER_DETAIL_BASE_PATH_V1+"/{id}")
    public OrderDetailDTO gerOrderDetailById (@PathVariable Long id) {
        return orderDetailService.getOrderDetailById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(ORDER_DETAIL_BASE_PATH_V1)
    public ResponseEntity createOrderDetail  (@RequestBody OrderDetailDTO orderDetail) {
        orderDetailService.createOrderDetail(orderDetail);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(ORDER_DETAIL_BASE_PATH_V2+"/{id}")
    public ResponseEntity updateOrderDetailById (@RequestBody OrderDetailDTO orderDetail, @PathVariable Long id) {
        if(orderDetailService.updateOrderDetail(orderDetail, id).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(ORDER_DETAIL_BASE_PATH_V2)
    public OrderDetailDTO getOrderDetailByOrder (@RequestBody CarOrderDTO carOrderDTO) {
        return orderDetailService.getOrderDetailByOrder(carOrderDTO).orElseThrow(NotFoundException::new);
    }

}
