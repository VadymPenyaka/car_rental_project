package nulp.cs.carrentalrestservice.modules.person.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.person.dto.VerificationType;
import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;
import nulp.cs.carrentalrestservice.modules.person.entity.PersonPendingConfirmation;
import nulp.cs.carrentalrestservice.modules.person.mapper.PersonMapper;
import nulp.cs.carrentalrestservice.modules.person.repository.PersonRepository;
import nulp.cs.carrentalrestservice.modules.security.dto.Role;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonRegistrationRequest;
import nulp.cs.carrentalrestservice.shared.exception.InvalidVerificationTokenException;
import nulp.cs.carrentalrestservice.shared.dto.request.UpdatePersonRequest;
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
    private final ConfirmationService tokenService;


    @Override
    public boolean isEmailUsed(String email) {
        return personRepository.existsByUsername(email);
    }

    @Override
    public boolean isPhoneNumberUsed(String number) {
        return personRepository.existsByPhoneNumber(number);
    }

    @Override
    public void createPendingPerson(PersonDTO personDTO) {
        personDTO.setPassword(passwordEncoder
                .encode(personDTO.getPassword()));

        tokenService.createToken(personDTO, VerificationType.REGISTRATION, personDTO.getUsername());
    }

    @Override
    public void registerPerson(PersonRegistrationRequest customerData) {
        PersonDTO personDTO = PersonDTO.builder()
                .phoneNumber(customerData.getPhoneNumber())
                .password(customerData.getPassword())
                .username(customerData.getEmail())
                .role(Role.ROLE_USER)
                .build();


        createPendingPerson(personDTO);
    }


    @Override
    public void createVerifiedPerson(String tokenStr){
        PersonPendingConfirmation confirmation = tokenService.confirmAndGet(UUID.fromString(tokenStr));
        PersonDTO person = confirmation.getData();

        if (confirmation.getType()!=VerificationType.REGISTRATION) {
            throw new InvalidVerificationTokenException("Invalid token type!");
        }

        personMapper.personToPersonDto(personRepository
                .save(personMapper.personDtoToPerson(person)));
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
        String targetEmail = person.getUsername();
        if (request.getEmail() != null) {
            person.setUsername(request.getEmail());
            tokenService.createToken(person, VerificationType.EMAIL, targetEmail);
        }
        if (request.getPhoneNumber() != null) {
            person.setPhoneNumber(request.getPhoneNumber());
            tokenService.createToken(person, VerificationType.PHONE, targetEmail);
        }
        if (request.getPassword() != null) {
            person.setPassword(request.getPassword());
            tokenService.createToken(person, VerificationType.PASSWORD, targetEmail);
        }
    }

    @Override
    public void verifyUpdate(String tokenStr) {
        PersonPendingConfirmation confirmation = tokenService.confirmAndGet(UUID.fromString(tokenStr));
        PersonDTO person = confirmation.getData();

        if (confirmation.getType()==VerificationType.EMAIL) {
            tokenService.createToken(person, VerificationType.NEW_EMAIL, person.getUsername());
        } else {
            personRepository.save(personMapper.personDtoToPerson(person));
        }
    }

}
