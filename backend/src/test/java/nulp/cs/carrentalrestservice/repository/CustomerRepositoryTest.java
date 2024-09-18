package nulp.cs.carrentalrestservice.repository;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    @Transactional
    void saveUserTest() {
        Customer customer = Customer.builder()
                .birthDate(LocalDate.of(2000, 10, 10))
                .passportExpiryDate(LocalDate.of(2030, 10, 10))
                .firstName("FirstName")
                .sureName("SureName")
                .email("email@gmail.com")
                .phoneNumber("0958925222")
                .passportId("111111111")
                .build();

        Customer savedCustomer = customerRepository.saveAndFlush(customer);

        assertThat(savedCustomer).isNotNull();
    }
}
