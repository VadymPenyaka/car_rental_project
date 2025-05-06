package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.RefreshToken;
import nulp.cs.carrentalrestservice.model.dto.RefreshTokenDTO;
import org.mapstruct.Mapper;

@Mapper
public interface RefreshTokenMapper {
    RefreshTokenDTO entityToDto (RefreshToken entity);

    RefreshToken dtoToEntity (RefreshTokenDTO dto);
}
