package nulp.cs.carrentalrestservice.modules.bankid.serivce;


import nulp.cs.carrentalrestservice.modules.bankid.dto.PersonalDataDTO;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.shared.dto.request.OrderCreationRequest;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonalInfoRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankIdVerificationService {

    void createCustomerFullInfo(PersonalInfoRequest customerDTO);

    void createRandomPersonalData();

    Optional<PersonalDataDTO> getAuthenticatedCustomerInfo();

    boolean verifyCustomerForOrder (OrderCreationRequest orderCreationRequest);

    //    TODO create method to get all customer orders from order service
    List<CarOrderDTO> getAllCustomerOrders(UUID id);
}
