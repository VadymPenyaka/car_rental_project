package nulp.cs.carrentalrestservice.service.person.customer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.CategoryVerificationException;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.*;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import nulp.cs.carrentalrestservice.model.request.PersonalInfoRequest;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.service.person.PersonalInfoService;
import nulp.cs.carrentalrestservice.service.person.PersonService;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.util.logging.LoggingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerInfoServiceImpl implements CustomerInfoService {
    private final PersonService personService;
    private final CarService carService;
    private final LoggingService loggingService;
    private final PersonalInfoService personalInfoService;

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
    public void createCustomerFullInfo(PersonalInfoRequest customerRequest) {
        try {
            UUID authenticatedPersonId = personService.getAuthenticatedPerson().getId();
            customerRequest.setPersonId(authenticatedPersonId);
            personalInfoService.createPerson(customerRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error receiving data from Bank ID", e);
        }
    }

    @Override
    public void createRandomPersonalData() {
        UUID authenticatedPersonId = personService.getAuthenticatedPerson().getId();
        personalInfoService.createPersonWithRandomData(authenticatedPersonId);
    }

    @Override
    @Transactional
    public Optional<PersonalDataDTO> getAuthenticatedCustomerInfo() {
        loggingService.logDebug("getAuthenticatedCustomer");
        PersonDTO person = personService.getAuthenticatedPerson();


        return  personalInfoService.getCustomerDataById(person.getId());
    }

    @Override
    @Transactional
    public boolean verifyCustomerForOrder(OrderCreationRequest orderCreationRequest) {
        Optional<PersonalDataDTO> personalDataOpt = getAuthenticatedCustomerInfo();

        if (personalDataOpt.isEmpty()) {
            throw new IllegalArgumentException("You did not provide required personal data.");
        }
        PersonalDataDTO personalDataDTO = personalDataOpt.get();
        if (
                personalDataDTO.getDriverLicense() == null ||
                personalDataDTO.getPassport() == null
        ) {
            throw new IllegalArgumentException("You did not provide all the required data.");
        }

        CarDTO carDTO = carService.getCarFullDetailsById(orderCreationRequest
                .getCarId()).orElseThrow(()->new NotFoundException("Car not found."));

        personalDataDTO.getDriverLicense().getCategories()
                .stream()
                .map(DriverLicenseCategoryDTO::getCategory)
                .filter(category -> category.equals(carDTO.getCarDetails().getLicenseCategory()))
                .findFirst()
                .orElseThrow(() ->
                        new CategoryVerificationException("You have not necessary category."));

        return personalDataDTO
                .getDriverLicense()
                .getIssueDate()
                .isBefore(LocalDate.now()
                        .minusYears(carDTO
                                .getCarDetails().getRequiredExperience()));
    }

//    TODO create method to get all customer orders from order service
    @Override
    public List<CarOrderDTO> getAllCustomerOrders(UUID id) {
        return null;
    }

}
