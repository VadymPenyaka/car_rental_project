package nulp.cs.carrentalrestservice.modules.person;

import nulp.cs.carrentalrestservice.shared.dto.request.UpdatePersonRequest;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    boolean isEmailUsed (String email);
    boolean isPhoneNumberUsed (String number);
    void createPendingPerson(PersonDTO personDTO);

    void createVerifiedPerson(String tokenStr);

    Optional<PersonDTO> getPersonById (UUID id);
    Optional<PersonDTO> getPersonByEmail (String email);
    PersonDTO getAuthenticatedPerson ();
    void updatePerson(UpdatePersonRequest request);
    void verifyUpdate(String tokenStr);
}
