package nulp.cs.carrentalrestservice.repository;

import nulp.cs.carrentalrestservice.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void getAllAdminsTest() {
        List<Admin> foundAdmins = adminRepository.findAll();

        assertThat(foundAdmins.size()).isEqualTo(2);
    }


    @Test
    void createAdminTest() {
        Admin admin = adminRepository.save(Admin.builder()
                        .email("admin@email.com")
                        .firstName("Admin")
                        .lastName("User")
                        .phoneNumber("123456789")
                        .build());


        assertThat(admin).isNotNull();
    }
}
