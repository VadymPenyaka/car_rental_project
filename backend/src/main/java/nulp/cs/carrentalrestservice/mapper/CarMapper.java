package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Car;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

public @Mapper(componentModel = "spring")interface CarMapper {
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "model", target = "model")
    Car carDtoToCar (CarDTO carDTO);
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "model", target = "model")
    CarDTO carToCarDto (Car car);
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "model.modelName", target = "modelName")
    @Mapping(source = "model.brandName.name", target = "brandName")
    CarCardResponse carToCarCardDto (Car car);
    @Mapping(source = "carPricing", target = "carPricing")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "model", target = "model")
    CarCustomerDetailsResponse carToCustomerDetailDto (Car car);
}
