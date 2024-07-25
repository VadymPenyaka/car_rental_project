package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.mapper.AdminMapper;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.repository.CarOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;


    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        return adminMapper.adminToAdminDto(adminRepository
                        .save(adminMapper.adminDtoToAdmin(adminDTO)));
    }

    @Override
    public Optional<AdminDTO> updateAdminById(Long id, AdminDTO admin) {
        AtomicReference<Optional<AdminDTO>> atomicReference = new AtomicReference<>();

        adminRepository.findById(id).ifPresentOrElse(foundAdmin -> {
            foundAdmin.setPassword(admin.getPassword());
            foundAdmin.setFirstName(admin.getFirstName());
            foundAdmin.setLastName(admin.getLastName());

            atomicReference.set(Optional.of(adminMapper
                    .adminToAdminDto(adminRepository.save(foundAdmin))));
        },()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Optional<AdminDTO> getAdminById(Long id) {
        return Optional.ofNullable(adminMapper.adminToAdminDto(adminRepository
                .findById(id).orElse(null)));
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::adminToAdminDto)
                .toList();
    }

    @Override
    public Boolean deleteAdminByID(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

//    @Override
//    public Optional<AdminDTO> getAdminByLeastNumbErOfOrders() {
//        Comparator<Admin> adminComparator = Comparator.comparingInt(a -> a.getCarOrders().size());
//
//        return Optional.ofNullable(adminMapper.adminToAdminDto(adminRepository
//                .findAll().stream().min(adminComparator).get()));
//    }

    @Override
    public Optional<AdminDTO> getAdminWithFewestOrders() {
        List<Admin> admins =  adminRepository.findAll();
        Collections.sort(admins);

        return Optional.ofNullable(adminMapper.adminToAdminDto(admins.get(0)));
    }




}
