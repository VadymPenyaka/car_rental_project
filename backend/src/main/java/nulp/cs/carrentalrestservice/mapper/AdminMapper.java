package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import org.mapstruct.Mapper;

@Mapper
public interface AdminMapper {
    Admin adminDtoToAdmin (AdminDTO adminDTO);

    AdminDTO adminToAdminDto (Admin admin);
}
