package nulp.cs.carrentalrestservice.service.person.admin;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.CarOrder;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.AdminMapper;
import nulp.cs.carrentalrestservice.model.dto.AdminDTO;
import nulp.cs.carrentalrestservice.model.dto.LocationDTO;
import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import nulp.cs.carrentalrestservice.model.enumeration.OrderStatus;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.service.person.PersonService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PersonService personService;
    private final LoggingService loggingService;

    @Override
    public void createAdmin(AdminDTO adminDTO) {
        loggingService.logInfo("Create admin for id: " + adminDTO.getId());
        PersonDTO person = adminDTO.getPerson();
        person.setRole(Role.ROLE_ADMIN);
        personService.createPerson(adminDTO.getPerson());

        adminMapper.adminToAdminDto(adminRepository
                .save(adminMapper.adminDtoToAdmin(adminDTO)));
    }

    @Override
    public Optional<AdminDTO> updateAdminById(UUID id, AdminDTO admin) {
        AtomicReference<Optional<AdminDTO>> atomicReference = new AtomicReference<>();
        loggingService.logInfo("Update admin for ID: " + id);

        adminRepository.findById(id).ifPresentOrElse(foundAdmin -> {
            foundAdmin.setOnVocation(admin.isOnVocation());
            if (admin.getDepartment() != null) {
                foundAdmin.setDepartment(admin.getDepartment());
            }
            if (admin.getPosition() != null) {
                foundAdmin.setPosition(admin.getPosition());
            }
            if (admin.getLocation() != null) {
                foundAdmin.setLocation(adminMapper.adminDtoToAdmin(admin).getLocation());
            }

            atomicReference.set(Optional.of(adminMapper
                .adminToAdminDto(adminRepository.save(foundAdmin))));
                }, () -> { loggingService.logInfo("Admin with ID: " + id + " not found");
                atomicReference.set(Optional.empty());
        });
        loggingService.logInfo("Admin is updated successfully");
        return atomicReference.get();
    }

    @Override
    public Optional<AdminDTO> getAdminById(UUID id) {
        loggingService.logInfo("Get admin with id: " + id);
        return Optional.ofNullable(adminMapper.adminToAdminDto(adminRepository
                .findById(id).orElse(null)));
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        loggingService.logInfo("Getting all admins");
        return adminRepository.findAll().stream()
                .map(adminMapper::adminToAdminDto)
                .toList();
    }

    @Override
    public Boolean deleteAdminByID(UUID id) {
        loggingService.logInfo("Delete admin for id: " + id);
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            loggingService.logInfo("Admin is deleted successfully");
            return true;
        }
        loggingService.logInfo("Admin with id: " + id+" not found");
        return false;
    }

    @Override
    public AdminDTO getAdminForOrderByLocation(LocationDTO locationDTO) {
        loggingService.logInfo("Getting admin with fewest orders");
        UUID locationId = locationDTO.getId();
        List<Admin> allAdmins = adminRepository.findAll();

        Admin admin = allAdmins.stream()
                .filter(a -> !a.isOnVocation() && a.getLocation() != null && Objects.equals(a.getLocation().getId(), locationId))
                .min(Comparator.comparingLong(a -> {
                    Set<CarOrder> orders = a.getCarOrders();
                    if (orders == null) return 0L;
                    return orders.stream()
                            .filter(o -> o.getStatus() == OrderStatus.IN_USE || o.getStatus() == OrderStatus.PAID)
                            .count();
                }))
                .orElseThrow(() -> new NotFoundException("No appropriate admin was found."));

        loggingService.logInfo("Admin was found");
        return adminMapper.adminToAdminDto(admin);
    }
}
