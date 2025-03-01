package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Passport;
import nulp.cs.carrentalrestservice.model.PassportDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PassportMapper {
    @Mapping(source = "document", target = "document")
    Passport passportDtoToPassport (PassportDTO passportDTO);

    @Mapping(source = "document", target = "document")
    PassportDTO passportToPassportDto (Passport passport);
}
