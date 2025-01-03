package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.mapper.AdminMapper;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AdminDTO createAdmin(AdminDTO adminDTO) {
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        return adminMapper.adminToAdminDto(adminRepository
                        .save(adminMapper.adminDtoToAdmin(adminDTO)));
    }

    @Override
    public Optional<AdminDTO> updateAdminById(UUID id, AdminDTO admin) {
        AtomicReference<Optional<AdminDTO>> atomicReference = new AtomicReference<>();

        adminRepository.findById(id).ifPresentOrElse(foundAdmin -> {
            foundAdmin.setPassword(admin.getPassword());
            foundAdmin.setFirstName(admin.getFirstName());
            foundAdmin.setLastName(admin.getLastName());
            foundAdmin.setEmail(admin.getEmail());
            foundAdmin.setPhoneNumber(admin.getPhoneNumber());

            atomicReference.set(Optional.of(adminMapper
                    .adminToAdminDto(adminRepository.save(foundAdmin))));
        },()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Optional<AdminDTO> getAdminById(UUID id) {
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
    public Boolean deleteAdminByID(UUID id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<AdminDTO> getAdminWithFewestOrders() {
        List<Admin> admins =  adminRepository.findAll();
        Collections.sort(admins);

        return Optional.ofNullable(adminMapper.adminToAdminDto(admins.get(0)));
    }

    @Override
    public boolean isOwner(UUID id, String username) {
        return adminRepository.findById(id)
                .map(admin -> admin.getEmail()
                .equals(username)).orElse(false);
    }


}
