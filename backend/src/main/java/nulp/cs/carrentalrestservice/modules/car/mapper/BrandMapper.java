package nulp.cs.carrentalrestservice.modules.car.mapper;

import nulp.cs.carrentalrestservice.modules.car.enity.Brand;
import nulp.cs.carrentalrestservice.modules.car.dto.BrandDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand brandDtoToBrand (BrandDTO brandDTO);
    BrandDTO brandToBrandDto (Brand brand);
}
