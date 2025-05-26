package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Model;
import nulp.cs.carrentalrestservice.model.dto.ModelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelMapper {
//    @Mapping(target = "brandName", source = "brandName")
    Model modelDtoToModel (ModelDTO modelDTO);
//    @Mapping(target = "brandName", source = "brandName")
    ModelDTO modelToModelDto (Model model);
}
