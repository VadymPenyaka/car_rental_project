package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.Exception.NotFoundException;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.OrderStatus;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarOrderServiceImpl implements CarOrderService {
    private final CarOrderRepository carOrderRepository;
    private final CarOrderMapper carOrderMapper;

    private final AdminRepository adminRepository;

    @Override
    public CarOrderDTO createCarOrder(CarOrderDTO carOrderDTO) {
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
            atomicReference.set(Optional.of(carOrderMapper.carOrderToCarOrderDto(carOrderRepository.save(foundOrder))));
        }, ()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

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
