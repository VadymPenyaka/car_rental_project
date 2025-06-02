package nulp.cs.carrentalrestservice.modules.car.mapper;

import nulp.cs.carrentalrestservice.modules.car.enity.Model;
import nulp.cs.carrentalrestservice.modules.car.dto.ModelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
//    @Mapping(target = "brandName", source = "brandName")
    Model modelDtoToModel (ModelDTO modelDTO);
//    @Mapping(target = "brandName", source = "brandName")
    ModelDTO modelToModelDto (Model model);
}
