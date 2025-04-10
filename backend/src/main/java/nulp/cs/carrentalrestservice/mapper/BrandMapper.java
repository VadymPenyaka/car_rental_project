package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Brand;
import nulp.cs.carrentalrestservice.model.dto.BrandDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BrandMapper {
    Brand brandDtoToBrand (BrandDTO brandDTO);
    BrandDTO brandToBrandDto (Brand brand);
}
