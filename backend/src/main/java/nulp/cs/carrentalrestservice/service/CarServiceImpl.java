package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.model.CarClass;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;


    @Override
    public CarDTO createCar(CarDTO carDTO) {
        return carMapper.carToCarDto(carRepository
                .save(carMapper.carDtoToCar(carDTO)));

    }

    @Override
    public Optional<CarDTO> getCarByID(Long id) {
        return Optional.ofNullable(carMapper
                .carToCarDto(carRepository.findById(id).orElse(null)));
    }

    @Override
    public List<CarDTO> getAllCars() {
        return carRepository.findAll().stream()
                .map(carMapper::carToCarDto).toList();
    }

    @Override
    public Boolean deleteCarById(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<CarDTO> updateCarByID(Long id, CarDTO carDTO) {
        AtomicReference<Optional<CarDTO>> atomicReference = new AtomicReference<>();

        carRepository.findById(id).ifPresentOrElse( foundCar -> {
                foundCar.setBrand(carDTO.getBrand());
                foundCar.setModel(carDTO.getModel());
                foundCar.setCarClass(carDTO.getCarClass());
                foundCar.setFuelType(carDTO.getFuelType());
                atomicReference.set(Optional.of(carMapper
                        .carToCarDto(carRepository.save(foundCar))));
            }, ()-> {
                atomicReference.set(Optional.empty());
            }
        );

        return atomicReference.get();
    }

    @Override
    public List<CarDTO> getCarsByCarClass(CarClass carClass) {

        return carRepository.findAllByCarClass(carClass).stream()
                .map(carMapper::carToCarDto).toList();
    }

    @Override
    public List<CarDTO> findAllActiveCarsInPeriod(LocalDate startDate, LocalDate endDate) {
        return carRepository.getAvailableCarsForPeriod(startDate, endDate).stream().map(carMapper::carToCarDto).toList();
    }


}
