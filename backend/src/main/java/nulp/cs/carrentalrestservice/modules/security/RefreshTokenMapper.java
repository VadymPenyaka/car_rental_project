package nulp.cs.carrentalrestservice.modules.security;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {
    RefreshTokenDTO entityToDto (RefreshToken entity);

    RefreshToken ToEntity(RefreshTokenDTO dto);
}
