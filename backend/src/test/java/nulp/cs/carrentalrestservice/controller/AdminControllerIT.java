package nulp.cs.carrentalrestservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.mapper.AdminMapper;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AdminControllerIT {
    @Autowired
    private AdminController adminController;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private Flyway flyway;

    private MockMvc mockMvc;



    @BeforeEach
    void setUp() {
        Flyway flyway = Flyway.configure()
                .locations("classpath:db/migration_test")
                .dataSource("jdbc:h2:mem:testdb", "root", "12345678")
                .load();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getAllAdminsTest() {
        List<AdminDTO> adminDTOS = adminController.getAllAdmins();

        assertThat(adminDTOS.size()).isNotEqualTo(0);
    }

    @Test
    @Rollback
    @Transactional
    void createAdminTest () {
        AdminDTO adminDTO = adminMapper
                .adminToAdminDto(adminRepository
                .findAll().get(0));

        ResponseEntity responseEntity = adminController.createAdmin(adminDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

    }

    @Test
    void getAdminByIdTest () {
        AdminDTO expected = adminMapper.adminToAdminDto(adminRepository.findAll().get(0));

        AdminDTO foundAdmin = adminController.getAdminById(expected.getId());

        assertThat(foundAdmin).isEqualTo(expected);
    }

    @Test
    @Rollback
    @Transactional
    void updateAdminById () {
        Admin admin = adminRepository.findAll().get(0);
        AdminDTO expected = adminMapper.adminToAdminDto(admin);

        final String updatedName = "UPDATED";
        expected.setFirstName(updatedName);

        ResponseEntity responseEntity = adminController.updateAdminById(expected.getId(), expected);
        Admin actual = adminRepository.findById(expected.getId()).get();

        AdminDTO actualDTO = adminMapper.adminToAdminDto(actual);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actualDTO).isEqualTo(expected);
    }

    @Test
    @Rollback
    @Transactional
    void deleteAdminById () {
        AdminDTO admin = adminMapper.adminToAdminDto(adminRepository.findAll().get(0));

        ResponseEntity responseEntity = adminController.deleteAdminById(admin.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(adminRepository.findById(admin.getId())).isEqualTo(Optional.empty());
    }


}