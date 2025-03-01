package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.response.CarCardDTO;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    Car carDtoToCar (CarDTO carDTO);
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    CarDTO carToCarDto (Car car);
    @Mapping(source = "carPricing", target = "carPricing")
    CarCardDTO carToCarCardDto (Car car);
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    CarCustomerDetailsDTO carToCustomerDetailDto (Car car);
}
