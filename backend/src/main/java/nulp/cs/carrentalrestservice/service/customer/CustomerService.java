package nulp.cs.carrentalrestservice.service.customer;

import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.CustomerDTO;
import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    PersonDTO registerCustomer (CustomerRegistrationRequest customerData);

    CustomerDTO createCustomerFullInfo(CustomerDTO customerDTO);

    Optional<CustomerDTO> getCustomerByID (UUID id);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO);

    CustomerDTO getAuthenticatedCustomer();

    boolean verifyCustomerForOrder (OrderCreationRequest orderCreationRequest);

    List<CarOrderDTO> getAllCustomerOrders (UUID id);
}
