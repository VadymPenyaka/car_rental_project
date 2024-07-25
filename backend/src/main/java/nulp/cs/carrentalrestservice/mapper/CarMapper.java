package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.CarDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarMapper {
    Car carDtoToCar (CarDTO carDTO);

    CarDTO carToCarDto (Car car);
}
