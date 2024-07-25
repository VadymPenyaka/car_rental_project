package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.AdminDTO;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    AdminDTO createAdmin (AdminDTO adminDTO);

    Optional<AdminDTO> updateAdminById (Long id, AdminDTO admin);

    Optional<AdminDTO> getAdminById (Long id);

    List<AdminDTO> getAllAdmins ();

    Boolean deleteAdminByID(Long id);

//    Optional<AdminDTO> getAdminByLeastNumbErOfOrders ();

    Optional<AdminDTO> getAdminWithFewestOrders();

}
