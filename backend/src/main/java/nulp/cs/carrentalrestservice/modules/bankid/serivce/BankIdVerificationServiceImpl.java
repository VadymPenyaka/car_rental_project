package nulp.cs.carrentalrestservice.modules.bankid.serivce;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.client.BankIdVerificationClient;
import nulp.cs.carrentalrestservice.modules.bankid.dto.DriverLicenseCategoryDTO;
import nulp.cs.carrentalrestservice.modules.bankid.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;
import nulp.cs.carrentalrestservice.shared.exception.CategoryVerificationException;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonalInfoRequest;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.modules.car.dto.CarDTO;
import nulp.cs.carrentalrestservice.modules.person.service.PersonService;
import nulp.cs.carrentalrestservice.modules.car.serivce.CarService;
import nulp.cs.carrentalrestservice.shared.logging.LoggingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankIdVerificationServiceImpl implements BankIdVerificationService {
    private final PersonService personService;
    private final CarService carService;
    private final LoggingService loggingService;
    private final BankIdVerificationClient bankIdVerificationClient;

    @Transactional
    public void createCustomerFullInfo(PersonalInfoRequest customerRequest) {
        try {
            UUID authenticatedPersonId = personService.getAuthenticatedPerson().getId();
            customerRequest.setPersonId(authenticatedPersonId);
            bankIdVerificationClient.createPerson(customerRequest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error receiving data from Bank ID", e);
        }
    }

    public void createRandomPersonalData() {
        UUID authenticatedPersonId = personService.getAuthenticatedPerson().getId();
        bankIdVerificationClient.createPersonWithRandomData(authenticatedPersonId);
    }

    @Transactional
    public Optional<PersonalDataDTO> getAuthenticatedCustomerInfo() {
        loggingService.logDebug("getAuthenticatedCustomer");
        PersonDTO person = personService.getAuthenticatedPerson();


        return  bankIdVerificationClient.getCustomerDataById(person.getId());
    }

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
public List<CarOrderDTO> getAllCustomerOrders(UUID id) {
        return null;
    }

}
