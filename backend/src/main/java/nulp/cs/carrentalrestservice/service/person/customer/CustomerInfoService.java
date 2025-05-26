package nulp.cs.carrentalrestservice.service.person.customer;

import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.model.request.PersonalInfoRequest;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.model.request.OrderCreationRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerInfoService {
    void registerCustomer (CustomerRegistrationRequest customerData);

    void createCustomerFullInfo(PersonalInfoRequest customerDTO);

    void createRandomPersonalData();

    Optional<PersonalDataDTO> getAuthenticatedCustomerInfo();

    boolean verifyCustomerForOrder (OrderCreationRequest orderCreationRequest);

    List<CarOrderDTO> getAllCustomerOrders (UUID id);
}
