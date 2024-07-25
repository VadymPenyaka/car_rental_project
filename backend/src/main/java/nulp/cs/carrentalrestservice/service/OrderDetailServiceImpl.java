package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.entity.OrderDetail;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.mapper.OrderDetailMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderDetailDTO;
import nulp.cs.carrentalrestservice.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    private final CarOrderService carOrderService;

    private final CarMapper carMapper;

    private final CarOrderMapper carOrderMapper;

    @Override
    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        return orderDetailMapper.orderDetailToOrderDetailDto(orderDetailRepository
                .save(orderDetailMapper.orderDetailDtoToOrderDetail(orderDetailDTO)));
    }

    @Override
    public Optional<OrderDetailDTO> getOrderDetailById(Long id) {
        return Optional.ofNullable(orderDetailMapper.orderDetailToOrderDetailDto(orderDetailRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<OrderDetailDTO> updateOrderDetail(OrderDetailDTO orderDetailDTO, Long id) {
        AtomicReference<Optional<OrderDetailDTO>> atomicReference = new AtomicReference<>();

        orderDetailRepository.findById(id).ifPresentOrElse( foundDetail -> {
                OrderDetailDTO foundDetailsDTO = orderDetailMapper.orderDetailToOrderDetailDto(foundDetail);
                foundDetailsDTO.setCar(orderDetailDTO.getCar());
                foundDetailsDTO.setNumberOfDays(orderDetailDTO.getNumberOfDays());
                foundDetailsDTO.setPickUpDate(orderDetailDTO.getPickUpDate());
                foundDetailsDTO.setDropOffDate(orderDetailDTO.getDropOffDate());
                foundDetailsDTO.setPickUpLocation(orderDetailDTO.getPickUpLocation());
                foundDetailsDTO.setDropOffLocation(orderDetailDTO.getDropOffLocation());
                foundDetailsDTO.setTotalPrice(orderDetailDTO.getTotalPrice());

                atomicReference.set(Optional.of(orderDetailMapper.orderDetailToOrderDetailDto(orderDetailRepository
                        .save(orderDetailMapper.orderDetailDtoToOrderDetail(foundDetailsDTO)))));
            }, ()-> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }

    @Override
    public void deleteAllOrderDetailByCar(CarDTO car) {
        List<OrderDetail> orderDetails = orderDetailRepository.getAllByCar(carMapper.carDtoToCar(car));

        for (OrderDetail orderDetail : orderDetails) {
            CarOrder carOrder = orderDetail.getCarOrder();
            carOrderService.deleteCarOrderById(carOrder.getId());
            deleteOrderDetailById(orderDetail.getId());
        }

    }

    @Override
    public boolean deleteOrderDetailById(Long id) {
        if (orderDetailRepository.existsById(id)) {
            orderDetailRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<OrderDetailDTO> getOrderDetailByOrder(CarOrderDTO carOrderDTO) {
        return Optional.ofNullable(orderDetailMapper.orderDetailToOrderDetailDto(orderDetailRepository
                .findOrderDetailByCarOrder(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO))));
    }
}
