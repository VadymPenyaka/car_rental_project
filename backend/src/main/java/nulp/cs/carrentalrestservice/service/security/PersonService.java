package nulp.cs.carrentalrestservice.service.security;

import nulp.cs.carrentalrestservice.model.dto.PersonDTO;

import java.util.Optional;
import java.util.UUID;

public interface PersonService {
    boolean isEmailUsed (String email);
    boolean deletePersonById (UUID id);
    boolean isPhoneNumberUsed (String number);
    PersonDTO createPerson (PersonDTO personDTO);
    Optional<PersonDTO> getPersonById (UUID id);
    Optional<PersonDTO> getPersonByEmail (String email);
    Optional<PersonDTO> updatePersonById (UUID id, PersonDTO personDTO);
    PersonDTO getAuthenticatedPerson ();
}
