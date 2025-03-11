package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.PersonDTO;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    PersonDTO registerCustomer (CustomerRegistrationRequest customerData);

    CustomerDTO createCustomerFullInfo(CustomerDTO customerDTO);

    Optional<CustomerDTO> getCustomerByID (UUID id);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO);

    CustomerDTO getAuthenticatedCustomer();

    boolean verifyCustomerForOrder (OrderCreationRequest orderCreationRequest);
}
