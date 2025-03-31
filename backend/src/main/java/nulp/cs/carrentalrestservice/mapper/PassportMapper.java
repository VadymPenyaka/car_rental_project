package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Passport;
import nulp.cs.carrentalrestservice.model.dto.PassportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PassportMapper {
    Passport passportDtoToPassport (PassportDTO passportDTO);

    PassportDTO passportToPassportDto (Passport passport);
}
