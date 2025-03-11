package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.CategoryVerificationException;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.CustomerMapper;
import nulp.cs.carrentalrestservice.model.CarDTO;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.PersonDTO;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final PersonService personService;
    private final CarService carService;
    private final LoggingService loggingService;

    @Override
    public PersonDTO registerCustomer(CustomerRegistrationRequest customerData) {
        PersonDTO personDTO = PersonDTO.builder()
                .phoneNumber(customerData.getPhoneNumber())
                .firstName(customerData.getFirstName())
                .sureName(customerData.getSureName())
                .password(customerData.getPassword())
                .username(customerData.getEmail())
                .role(Role.USER)
                .build();

        return personService.createPerson(personDTO);
    }

    @Override
    public CustomerDTO createCustomerFullInfo(CustomerDTO customerDTO) {
        loggingService.logInfo("Creating customer full information for id: "+ customerDTO.getId());

        return customerMapper.customerToCustomerDto(customerRepository
                .save(customerMapper.customerDtoToCustomer(customerDTO)));
    }

    @Override
    public Optional<CustomerDTO> getCustomerByID(UUID id) {
        loggingService.logInfo("Getting customer for ID: " + id);
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository
                .findById(id).orElse(null)));
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customerDTO) {
        loggingService.logInfo("Updating customer for ID: " + id);
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse( foundCustomer -> {
                    atomicReference.set(Optional.ofNullable(customerMapper
                            .customerToCustomerDto(customerRepository.save(foundCustomer))));
                    loggingService.logInfo("Customer updated successfully");
                }, ()-> {
                    atomicReference.set(Optional.empty());
                    loggingService.logInfo("Customer not found for ID: "+id);
                }
        );

        return atomicReference.get();
    }

    @Override
    public CustomerDTO getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("You should log in");
        }

        if (!(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new IllegalArgumentException("Invalid authentication principal");
        }

        Optional<PersonDTO> personOpt = personService.getPersonByEmail(userDetails.getUsername());

        if (personOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found by email: " + userDetails.getUsername());
        }

        UUID personId = personOpt.get().getId();
        return customerRepository.findCustomersByPersonId(personId)
                .map(customerMapper::customerToCustomerDto).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean verifyCustomerForOrder(OrderCreationRequest orderCreationRequest) {
        CustomerDTO customerDTO = getAuthenticatedCustomer();


        if (    customerDTO == null ||
                customerDTO.getDriverLicenses() == null ||
                customerDTO.getPassport() == null
        ) {
            throw new IllegalArgumentException("You did not provide all the required data.");
        }

        CarDTO carDTO = carService.getCarFullDetailsById(orderCreationRequest
                .getCarId()).orElseThrow(()->new NotFoundException("Car not found."));

        customerDTO.getDriverLicenses().getCategory()
                .stream()
                .filter(category -> category.equals(carDTO.getLicenseCategory().toString()))
                .findFirst()
                .orElseThrow(() ->
                        new CategoryVerificationException("You have not necessary category."));

        return customerDTO
                .getDriverLicenses()
                .getIssueDate()
                .isBefore(LocalDate.now()
                        .minusYears(carDTO
                                .getRequiredExperience()));
    }

}
