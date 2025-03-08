package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Model;
import nulp.cs.carrentalrestservice.model.ModelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ModelMapper {
    @Mapping(target = "brandName", source = "brandName")
    Model modelDtoToModel (ModelDTO modelDTO);
    @Mapping(target = "brandName", source = "brandName")
    ModelDTO modelToModelDto (Model model);
}
