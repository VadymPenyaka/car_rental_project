package nulp.cs.carrentalrestservice.repository;

import jakarta.persistence.Id;
import nulp.cs.carrentalrestservice.bootstrap.Bootstrap;
import nulp.cs.carrentalrestservice.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(Bootstrap.class)
public class AdminRepositoryTest {
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void getAllAdminsTest() {
        List<Admin> foundAdmins = adminRepository.findAll();

        assertThat(foundAdmins.size()).isEqualTo(1);
    }
}
