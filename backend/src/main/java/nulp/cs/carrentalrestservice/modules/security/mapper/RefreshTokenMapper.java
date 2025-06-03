package nulp.cs.carrentalrestservice.modules.security.mapper;

import nulp.cs.carrentalrestservice.modules.security.entity.RefreshToken;
import nulp.cs.carrentalrestservice.modules.security.dto.RefreshTokenDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenDTO entityToDto (RefreshToken entity);

    RefreshToken ToEntity(RefreshTokenDTO dto);
}
