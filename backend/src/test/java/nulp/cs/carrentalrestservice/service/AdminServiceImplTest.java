package nulp.cs.carrentalrestservice.service;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.mapper.AdminMapperImpl;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private AdminMapperImpl adminMapper;
    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;
    private AdminDTO adminDTO;

    @BeforeEach
    void setUp() {
        admin = Admin.builder()
                .password("password")
                .firstName("FirstName")
                .lastName("LastName")
                .build();

        adminDTO = AdminDTO.builder()
                .password("password")
                .firstName("FirstName")
                .lastName("LastName")
                .build();
    }

    @Test
    void createAdminTest() {
        when(adminRepository.save(any())).thenReturn(admin);
        when(adminMapper.adminToAdminDto(any())).thenReturn(adminDTO);

        AdminDTO savedAdminDTO = adminService.createAdmin(adminDTO);

        assertThat(savedAdminDTO).isNotNull();
        assertThat(savedAdminDTO.getFirstName()).isEqualTo(adminDTO.getFirstName());
    }

    @Test
    void getAdminByIdTest() {
        when(adminRepository.findById(any())).thenReturn(Optional.ofNullable(admin));
        when(adminMapper.adminToAdminDto(any())).thenReturn(adminDTO);

        AdminDTO  foundAdmin = adminService.getAdminById(adminDTO.getId()).get();

        assertThat(foundAdmin).isNotNull();
        assertThat(foundAdmin.getFirstName()).isEqualTo(adminDTO.getFirstName());
    }

    @Test
    void getAllAdminsTest() {
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));

        List<AdminDTO> foundAdmins = adminService.getAllAdmins();

        assertThat(foundAdmins.size()).isEqualTo(1);
    }

    @Test
    void deleteAdminByIDTest_ReturnTrue() {
        when(adminRepository.existsById(any())).thenReturn(true);

        boolean isDeleted = adminService.deleteAdminByID(adminDTO.getId());
        verify(adminRepository).deleteById(adminDTO.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void deleteAdminByIDTest_ReturnFalse() {
        when(adminRepository.existsById(any())).thenReturn(false);

        boolean isDeleted = adminService.deleteAdminByID(adminDTO.getId());


        assertThat(isDeleted).isFalse();
    }

    @Test
    @Transactional
    @Rollback
    void updateAdminById() {
        when(adminRepository.findById(any())).thenReturn(Optional.of(admin));
        when(adminRepository.save(any())).thenReturn(admin);
        when(adminMapper.adminToAdminDto(any())).thenReturn(adminDTO);

        AdminDTO expected = adminDTO;
        String updatedName = "UPDATED";
        expected.setFirstName(updatedName);

        AdminDTO actual = adminService.updateAdminById(expected.getId(), expected).get();


        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getAdminWithFewestOrders() {
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin));
        when(adminMapper.adminToAdminDto(any())).thenReturn(adminDTO);


        AdminDTO actual = adminService.getAdminWithFewestOrders().get();

        assertThat(actual).isEqualTo(adminDTO);
    }
}