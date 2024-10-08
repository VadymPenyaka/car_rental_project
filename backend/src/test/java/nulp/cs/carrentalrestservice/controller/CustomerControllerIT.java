package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.mapper.CustomerMapper;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class CustomerControllerIT {
    @Autowired
    private CustomerController controller;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;


    @Test
    @Rollback
    @Transactional
    void getCustomerByIdTest() {
        Customer expected = customerRepository.findAll().get(0);

        CustomerDTO actual = controller.getCustomerById(expected.getId());

        assertThat(customerMapper.customerToCustomerDto(expected)).isEqualTo(actual);
    }

    @Test
    @Rollback
    @Transactional
    void createCustomerTest() {
        Customer customer = customerRepository.findAll().get(0);

        CustomerDTO customerToCreate = customerMapper.customerToCustomerDto(customer);

        ResponseEntity responseEntity = controller.createCustomer(customerToCreate);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Customer savedCustomer = customerRepository.findById(customerToCreate.getId()).get();

        assertThat(savedCustomer).isNotNull();
    }

    @Test
    @Rollback
    @Transactional
    void updateCustomerById () {
        CustomerDTO expected = customerMapper.customerToCustomerDto(customerRepository.findAll().get(0));
        expected.setFirstName("updated");

        ResponseEntity responseEntity = controller.updateCustomerById(expected.getId(), expected);

        CustomerDTO actual = customerMapper.customerToCustomerDto(customerRepository
                .findById(expected.getId()).get());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(actual).isEqualTo(expected);
    }

}