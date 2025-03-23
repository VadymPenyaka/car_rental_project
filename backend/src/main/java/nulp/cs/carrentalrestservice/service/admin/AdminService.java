package nulp.cs.carrentalrestservice.service.admin;

import nulp.cs.carrentalrestservice.model.dto.AdminDTO;
import nulp.cs.carrentalrestservice.model.dto.LocationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    void createAdmin (AdminDTO adminDTO);

    Optional<AdminDTO> updateAdminById (UUID id, AdminDTO admin);

    Optional<AdminDTO> getAdminById (UUID id);

    List<AdminDTO> getAllAdmins ();

    Boolean deleteAdminByID(UUID id);

    AdminDTO getAdminForOrderByLocation(LocationDTO location);

}
