package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.model.dto.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdminMapper {
    @Mapping(source = "person", target = "person")
    Admin adminDtoToAdmin (AdminDTO adminDTO);
    @Mapping(source = "person", target = "person")
    AdminDTO adminToAdminDto (Admin admin);
}
