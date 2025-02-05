package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CustomerDTO;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    CustomerDTO createCustomer (CustomerDTO customerDTO);

    Optional<CustomerDTO> getCustomerByID (UUID id);

    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO);

    boolean isOwner (UUID id, String username);

    boolean isEmailUsed (String email);
    boolean isPassportIdUsed (String passportId);
    boolean isPhoneNumberUsed (String phoneNumber);
}
