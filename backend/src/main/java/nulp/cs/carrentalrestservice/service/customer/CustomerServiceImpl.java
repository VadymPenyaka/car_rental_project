package nulp.cs.carrentalrestservice.service.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.DriverLicense;
import nulp.cs.carrentalrestservice.exception.CategoryVerificationException;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.CarOrderMapper;
import nulp.cs.carrentalrestservice.mapper.CustomerMapper;
import nulp.cs.carrentalrestservice.model.dto.*;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import nulp.cs.carrentalrestservice.model.request.CustomerFullInfoRequest;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import nulp.cs.carrentalrestservice.service.document.DriverLicenseService;
import nulp.cs.carrentalrestservice.service.document.PassportService;
import nulp.cs.carrentalrestservice.service.security.PersonService;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final PersonService personService;
    private final DriverLicenseService licenseService;
    private final CarOrderMapper carOrderMapper;
    private final CarService carService;
    private final LoggingService loggingService;

    @Override
    public void registerCustomer(CustomerRegistrationRequest customerData) {
        PersonDTO personDTO = PersonDTO.builder()
                .phoneNumber(customerData.getPhoneNumber())
                .firstName(customerData.getFirstName())
                .sureName(customerData.getSureName())
                .password(customerData.getPassword())
                .username(customerData.getEmail())
                .role(Role.USER)
                .build();

        personService.createPerson(personDTO);
    }

    @Override
    @Transactional
    public void createCustomerFullInfo(CustomerFullInfoRequest customerRequest) {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .passport(customerRequest.getPassport())
                .person(personService.getAuthenticatedPerson())
                .driverLicense(licenseService.createDriverLicense(customerRequest.getDriverLicense()))
                .build();

        customerMapper.customerToCustomerDto(customerRepository
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
    @Transactional
    public CustomerDTO getAuthenticatedCustomer() {
        UUID personId = personService.getAuthenticatedPerson().getId();

        return customerRepository.findCustomerByPersonId(personId)
                .map(customerMapper::customerToCustomerDto).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean verifyCustomerForOrder(OrderCreationRequest orderCreationRequest) {
        CustomerDTO customerDTO = getAuthenticatedCustomer();


        if (
                customerDTO.getDriverLicense() == null ||
                customerDTO.getPassport() == null
        ) {
            throw new IllegalArgumentException("You did not provide all the required data.");
        }

        CarDTO carDTO = carService.getCarFullDetailsById(orderCreationRequest
                .getCarId()).orElseThrow(()->new NotFoundException("Car not found."));

        customerDTO.getDriverLicense().getCategories()
                .stream()
                .filter(category -> category.equals(carDTO.getLicenseCategory().toString()))
                .findFirst()
                .orElseThrow(() ->
                        new CategoryVerificationException("You have not necessary category."));

        return customerDTO
                .getDriverLicense()
                .getIssueDate()
                .isBefore(LocalDate.now()
                        .minusYears(carDTO
                                .getRequiredExperience()));
    }

    @Override
    public List<CarOrderDTO> getAllCustomerOrders(UUID id) {
        return customerRepository.getAllCustomerOrders(id)
                .stream().map(carOrderMapper::carOrderToCarOrderDto).toList();
    }

}
