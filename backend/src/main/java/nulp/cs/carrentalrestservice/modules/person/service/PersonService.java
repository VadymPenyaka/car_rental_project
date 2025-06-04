package nulp.cs.carrentalrestservice.modules.person.service;

import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonRegistrationRequest;
import nulp.cs.carrentalrestservice.shared.dto.request.UpdatePersonRequest;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    boolean isEmailUsed (String email);
    boolean isPhoneNumberUsed (String number);
    void createPendingPerson(PersonDTO personDTO);

    void registerPerson(PersonRegistrationRequest customerData);

    void createVerifiedPerson(String tokenStr);

    Optional<PersonDTO> getPersonById (UUID id);
    Optional<PersonDTO> getPersonByEmail (String email);
    PersonDTO getAuthenticatedPerson ();
    void updatePerson(UpdatePersonRequest request);
    void verifyUpdate(String tokenStr);
}
