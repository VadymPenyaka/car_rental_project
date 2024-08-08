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
                .birthDate(LocalDate.now())
                .expiryDate(LocalDate.now())
                .firstName("FirstName")
                .lastName("LastName")
                .sureName("SureName")
                .passportId("12345678")
                .build();

        Customer savedCustomer = customerRepository.saveAndFlush(customer);

        assertThat(savedCustomer).isNotNull();
    }
}
