package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerDTO createCustomer (CustomerDTO customerDTO);

    Optional<CustomerDTO> getCustomerByID (Long id);

    Optional<CustomerDTO> updateCustomerById(Long id, CustomerDTO customerDTO);

    List<CustomerDTO> getCustomersByLastName(String lastName);
}
