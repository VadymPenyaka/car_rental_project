package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.VerificationToken;
import nulp.cs.carrentalrestservice.model.dto.VerificationTokenDTO;
import org.mapstruct.Mapper;

@Mapper
public interface VerificationTokenMapper {
    VerificationToken toEntity (VerificationTokenDTO dto);
    VerificationTokenDTO toDto (VerificationToken entity);
}
