package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CustomerMapper;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        if (customerRepository.existsByPhoneNumber(customerDTO.getPhoneNumber())) {
            throw new IllegalArgumentException("User with this phone number already exists");
        }

        return customerMapper.customerToCustomerDto(customerRepository
                .save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> getCustomerByID(UUID id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository
                .findById(id).orElse(null)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse( foundCustomer -> {
                    foundCustomer.setBirthDate(customerDTO.getBirthDate());
                    foundCustomer.setPassportExpiryDate(customerDTO.getPassportExpiryDate());
                    foundCustomer.setFirstName(customerDTO.getFirstName());
                    foundCustomer.setPassportId(customerDTO.getPassportId());
                    foundCustomer.setSureName(customerDTO.getSureName());
                    foundCustomer.setEmail(customerDTO.getEmail());
                    foundCustomer.setPhoneNumber(customerDTO.getPhoneNumber());

                    atomicReference.set(Optional.ofNullable(customerMapper
                            .customerToCustomerDto(customerRepository.save(foundCustomer))));

                }, ()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public boolean isOwner(UUID id, String username) {
        return customerRepository.findById(id)
                .map(customer -> customer.getEmail()
                .equals(username)).orElse(false);
    }

}
