package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CustomerMapper;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return customerMapper.customerToCustomerDto(customerRepository
                .save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> getCustomerByID(Long id) {
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository
                .findById(id).orElse(null)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(Long id, CustomerDTO customerDTO) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse( foundCustomer -> {
                    foundCustomer.setBirthDate(customerDTO.getBirthDate());
                    foundCustomer.setExpiryDate(customerDTO.getExpiryDate());
                    foundCustomer.setFirstName(customerDTO.getFirstName());
                    foundCustomer.setLastName(customerDTO.getLastName());
                    foundCustomer.setPassportId(customerDTO.getPassportId());
                    foundCustomer.setSureName(customerDTO.getSureName());

                    atomicReference.set(Optional.ofNullable(customerMapper
                            .customerToCustomerDto(customerRepository.save(foundCustomer))));

                }, ()-> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public List<CustomerDTO> getCustomersByLastName(String lastName) {
        return customerRepository.findAllByLastName(lastName).stream()
                .map(customerMapper::customerToCustomerDto).toList();
    }
}
