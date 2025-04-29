package nulp.cs.carrentalrestservice.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.PersonMapper;
import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import nulp.cs.carrentalrestservice.repository.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;


    @Override
    public boolean isEmailUsed(String email) {
        return personRepository.existsByUsername(email);
    }

    @Override
    public boolean deletePersonById(UUID id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPhoneNumberUsed(String number) {
        return personRepository.existsByPhoneNumber(number);
    }

    @Override
    public PersonDTO createPerson(PersonDTO personDTO) {
        personDTO.setPassword(passwordEncoder
                .encode(personDTO.getPassword()));


        return personMapper.personToPersonDto(personRepository
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
    public Optional<PersonDTO> updatePersonById(UUID id, PersonDTO personDTO) {
        AtomicReference<Optional<PersonDTO>> atomicReference = new AtomicReference<>();
        personRepository.findById(id).ifPresentOrElse( foundPerson -> {
                foundPerson.setPhoneNumber(personDTO.getPhoneNumber());
                foundPerson.setUsername(personDTO.getUsername());
                foundPerson.setPassword(personDTO.getPassword());

                atomicReference.set(Optional.ofNullable(personMapper
                                .personToPersonDto(personRepository.save(foundPerson))));
            }, () -> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
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
}
