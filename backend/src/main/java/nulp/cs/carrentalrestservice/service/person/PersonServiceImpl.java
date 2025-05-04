package nulp.cs.carrentalrestservice.service.person;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.PersonMapper;
import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import nulp.cs.carrentalrestservice.model.dto.VerificationTokenDTO;
import nulp.cs.carrentalrestservice.model.enumeration.VerificationType;
import nulp.cs.carrentalrestservice.model.request.UpdatePersonRequest;
import nulp.cs.carrentalrestservice.repository.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;
    private final VerificationTokenService tokenService;


    @Override
    public boolean isEmailUsed(String email) {
        return personRepository.existsByUsername(email);
    }

    @Override
    public boolean isPhoneNumberUsed(String number) {
        return personRepository.existsByPhoneNumber(number);
    }

    @Override
    public void createPerson(PersonDTO personDTO) {
        personDTO.setPassword(passwordEncoder
                .encode(personDTO.getPassword()));


        personMapper.personToPersonDto(personRepository
                .save(personMapper.personDtoToPerson(personDTO)));
    }

    @Override
    public Optional<PersonDTO> getPersonById(UUID id) {
        return Optional.ofNullable(personMapper
                .personToPersonDto(personRepository
                        .findById(id).orElse(null)));
    }

    @Override
    public Optional<PersonDTO> getPersonByEmail(String email) {
        return Optional.ofNullable(personMapper
                .personToPersonDto(personRepository
                        .findByUsername(email).orElse(null)));
    }

    @Override
    public PersonDTO getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("You should log in");
        }

        if (!(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            throw new IllegalArgumentException("Invalid authentication principal");
        }

        return getPersonByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + userDetails.getUsername()));
    }

    @Override
    public void updatePerson(UpdatePersonRequest request) {
        PersonDTO person = getAuthenticatedPerson();
        if (request.getEmail() != null) {
            tokenService.createToken(person, request.getEmail(), VerificationType.EMAIL);
        }
        if (request.getPhoneNumber() != null) {
            tokenService.createToken(person, request.getPhoneNumber(), VerificationType.PHONE);
        }
        if (request.getPassword() != null) {
            tokenService.createToken(person, request.getPassword(), VerificationType.PASSWORD);
        }
    }

    @Override
    public void verifyUpdate(String tokenStr) {
        VerificationTokenDTO token = tokenService.verifyAndGetToken(UUID.fromString(tokenStr));
        PersonDTO person = token.getPerson();
        if (token.getType().equals(VerificationType.PASSWORD)) {
            person.setPassword(token.getValue());
        }
        if (token.getType().equals(VerificationType.NEW_EMAIL)) {
            person.setUsername(token.getValue());
        }
        if (token.getType().equals(VerificationType.PHONE)) {
            person.setPhoneNumber(token.getValue());
        }

        personRepository.save(personMapper.personDtoToPerson(person));
    }

}
