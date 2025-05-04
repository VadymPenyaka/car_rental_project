package nulp.cs.carrentalrestservice.service.person;

import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import nulp.cs.carrentalrestservice.model.request.UpdatePersonRequest;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    boolean isEmailUsed (String email);
    boolean isPhoneNumberUsed (String number);
    void createPerson (PersonDTO personDTO);
    Optional<PersonDTO> getPersonById (UUID id);
    Optional<PersonDTO> getPersonByEmail (String email);
    PersonDTO getAuthenticatedPerson ();
    void updatePerson(UpdatePersonRequest request);

    void verifyUpdate(String tokenStr);
}
