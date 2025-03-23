package nulp.cs.carrentalrestservice.util;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.repository.BrandRepository;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataGeneratorService {
    private final CarRepository carRepository;
    private final CarGenerator carGenerator;
    private final BrandRepository brandRepository;

    public void generateCars (int size) {
        carRepository.deleteAll();
        List<Car> cars = carGenerator.generateCarList(size);
        brandRepository.saveAll(carGenerator.brands);
        carRepository.saveAll(cars);
    }
}
