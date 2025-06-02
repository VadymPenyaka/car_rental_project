package nulp.cs.carrentalrestservice.modules.car.mapper;

import nulp.cs.carrentalrestservice.modules.car.enity.CarPricing;
import nulp.cs.carrentalrestservice.modules.car.dto.CarPricingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarPricingMapper {
    CarPricingDTO carPricingToCarPricingDto (CarPricing carPricing);
    CarPricing carPricingDtoToCarPricing (CarPricingDTO carPricingDTO);
}
