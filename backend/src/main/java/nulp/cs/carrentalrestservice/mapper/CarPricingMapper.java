package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.CarPricing;
import nulp.cs.carrentalrestservice.model.CarPricingDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CarPricingMapper {
    CarPricingDTO carPricingToCarPricingDto (CarPricing carPricing);
    CarPricing carPricingDtoToCarPricing (CarPricingDTO carPricingDTO);
}
