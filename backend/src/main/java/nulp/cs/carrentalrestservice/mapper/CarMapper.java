package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
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
    CarCardResponse carToCarCardDto (Car car);
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    CarCustomerDetailsResponse carToCustomerDetailDto (Car car);
}
