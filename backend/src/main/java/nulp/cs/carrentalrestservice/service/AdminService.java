package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.AdminDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AdminService {
    AdminDTO createAdmin (AdminDTO adminDTO);

    Optional<AdminDTO> updateAdminById (UUID id, AdminDTO admin);

    Optional<AdminDTO> getAdminById (UUID id);

    List<AdminDTO> getAllAdmins ();

    Boolean deleteAdminByID(UUID id);

    Optional<AdminDTO> getAdminWithFewestOrders();

}
