package nulp.cs.carrentalrestservice.modules.admin.mapper;

import nulp.cs.carrentalrestservice.modules.admin.enity.Admin;
import nulp.cs.carrentalrestservice.modules.admin.dto.AdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    @Mapping(source = "person", target = "person")
    Admin adminDtoToAdmin (AdminDTO adminDTO);
    @Mapping(source = "person", target = "person")
    AdminDTO adminToAdminDto (Admin admin);
}
