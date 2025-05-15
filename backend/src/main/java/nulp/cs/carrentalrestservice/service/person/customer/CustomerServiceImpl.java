package nulp.cs.carrentalrestservice.service.person.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import nulp.cs.carrentalrestservice.service.person.PersonService;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.util.logging.LoggingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
                .role(Role.ROLE_USER)
                .build();


        personService.createPendingPerson(personDTO);
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
    @Transactional
    public CustomerDTO getAuthenticatedCustomer() {
        loggingService.logDebug("getAuthenticatedCustomer");
        UUID personId = personService.getAuthenticatedPerson().getId();

        return customerRepository.findCustomerByPersonId(personId)
                .map(customerMapper::customerToCustomerDto).orElseThrow(() -> new NotFoundException("You should provide passport and license data."));
    }

    @Override
    @Transactional
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
                .map(DriverLicenseCategoryDTO::getCategory)
                .filter(category -> category.equals(carDTO.getCarDetails().getLicenseCategory()))
                .findFirst()
                .orElseThrow(() ->
                        new CategoryVerificationException("You have not necessary category."));

        return customerDTO
                .getDriverLicense()
                .getIssueDate()
                .isBefore(LocalDate.now()
                        .minusYears(carDTO
                                .getCarDetails().getRequiredExperience()));
    }

    @Override
    public List<CarOrderDTO> getAllCustomerOrders(UUID id) {
        return customerRepository.getAllCustomerOrders(id)
                .stream().map(carOrderMapper::carOrderToCarOrderDto).toList();
    }

}
