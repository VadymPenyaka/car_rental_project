package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.event.EmailEvent;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarOrderServiceImpl implements CarOrderService {
    private final CarOrderRepository carOrderRepository;
    private final CarOrderMapper carOrderMapper;

    private final AdminRepository adminRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public CarOrderDTO createCarOrder(CarOrderDTO carOrderDTO) {
        carOrderDTO.setEndDate(carOrderDTO.getEndDate().plusDays(carOrderDTO.getServiceDuration()));
        boolean hasOverlap = carOrderRepository.existsByCarIdAndDatesOverlap(
                carOrderDTO.getCar().getId(), carOrderDTO.getStartDate(), carOrderDTO.getEndDate());

        if(hasOverlap)
            throw new IllegalArgumentException("Car already overlap");


        return carOrderMapper.carOrderToCarOrderDto(carOrderRepository
                .save(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO)));
    }

    @Override
    public Optional<CarOrderDTO> getCarOrderByID(Long id) {
        return Optional.ofNullable(carOrderMapper.carOrderToCarOrderDto(carOrderRepository
                .findById(id).orElse(null)));
    }

    @Override
    public boolean deleteCarOrderById(Long id) {
        if (carOrderRepository.existsById(id)) {
            carOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CarOrderDTO> updateCarOrderById(Long id, CarOrderDTO carOrderDTO) {
        AtomicReference<Optional<CarOrderDTO>> atomicReference = new AtomicReference<>();

        carOrderRepository.findById(id).ifPresentOrElse(foundOrder -> {
            foundOrder.setStatus(carOrderDTO.getStatus());
            CustomerDTO customer = carOrderDTO.getCustomer();
            publisher.publishEvent(new EmailEvent(this, carOrderDTO, customer));
            atomicReference.set(Optional.of(carOrderMapper.carOrderToCarOrderDto(carOrderRepository.save(foundOrder))));
        }, ()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
//
//    @Override
//    public CarOrderDTO updateOrderServiceDuration(CarOrderDTO carOrderDTO, Integer newServiceDuration) {
//        if (Objects.equals(newServiceDuration, carOrderDTO.getServiceDuration()))
//            return carOrderDTO;
//        else if (newServiceDuration <= carOrderDTO.getServiceDuration()) {
//            long durationDifference = carOrderDTO.getServiceDuration() - newServiceDuration;
//            carOrderDTO.setEndDate(carOrderDTO.getEndDate().minusDays(durationDifference));
//            return carOrderDTO;
//        }
//
//        long serviceDurationExtension = newServiceDuration - carOrderDTO.getServiceDuration();
//        LocalDate endDateOfServiceDuration = carOrderDTO.getEndDate().plusDays(serviceDurationExtension);
//
//        boolean hasOverlap = carOrderRepository.existsByCarIdAndDatesOverlap(
//                carOrderDTO.getCar().getId(), carOrderDTO.getStartDate(), endDateOfServiceDuration);
//
//        if(hasOverlap)
//            throw new IllegalArgumentException("The next order for this car must be cancelled!");
//
//        carOrderDTO.setEndDate(endDateOfServiceDuration);
//        carOrderDTO.setServiceDuration(newServiceDuration);
//
//        return carOrderMapper.carOrderToCarOrderDto(carOrderRepository
//                .save(carOrderMapper.carOrderDtoToCarOrder(carOrderDTO)));
//    }

    @Override
    public List<CarOrderDTO> getAllCarOrdersByStatus(OrderStatus orderStatus) {
        return carOrderRepository.getAllByStatus(orderStatus).stream()
                .map(carOrderMapper::carOrderToCarOrderDto).toList();
    }

    @Override
    public List<CarOrderDTO> getCarOrdersByAdminAndStatus(Long adminId, OrderStatus orderStatus) throws NumberFormatException{

        Admin admin = adminRepository.findById(adminId).orElseThrow(NotFoundException::new);

        return carOrderRepository.getCarOrdersByAdminAndStatus(admin, orderStatus).stream()
                .map(carOrderMapper::carOrderToCarOrderDto).toList();
    }

}
