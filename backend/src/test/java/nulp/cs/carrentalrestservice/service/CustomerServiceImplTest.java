package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.mapper.CustomerMapper;
import nulp.cs.carrentalrestservice.mapper.CustomerMapperImpl;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapperImpl customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .birthDate(LocalDate.now())
                .expiryDate(LocalDate.now())
                .firstName("FirstName")
                .lastName("LastName")
                .sureName("SureName")
                .passportId("12345678")
                .build();

        customerDTO = CustomerDTO.builder()
                .birthDate(LocalDate.now())
                .expiryDate(LocalDate.now())
                .firstName("FirstName")
                .lastName("LastName")
                .sureName("SureName")
                .passportId("12345678")
                .build();
    }

    @Test
    void createCustomer() {
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerMapper.customerToCustomerDto(any())).thenReturn(customerDTO);

        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);

        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.getId()).isEqualTo(customerDTO.getId());
    }

    @Test
    void getCustomerByID() {
        when(customerRepository.findById(any())).thenReturn(Optional.ofNullable(customer));
        when(customerMapper.customerToCustomerDto(any())).thenReturn(customerDTO);

        CustomerDTO foundCustomer = customerService.getCustomerByID(customerDTO.getId()).get();

        assertThat(foundCustomer).isNotNull();
    }
}