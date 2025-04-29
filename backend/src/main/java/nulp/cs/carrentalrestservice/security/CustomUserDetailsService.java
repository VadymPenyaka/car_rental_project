package nulp.cs.carrentalrestservice.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Person;
import nulp.cs.carrentalrestservice.model.dto.PersonDetails;
import nulp.cs.carrentalrestservice.repository.PersonRepository;
import nulp.cs.carrentalrestservice.util.SensitiveDataConverter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final SensitiveDataConverter sensitiveDataConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Person> personOptional = personRepository.findByUsername(username);
        System.out.println();
        if (personOptional.isPresent()) {
            return PersonDetails.builder()
                    .person(personOptional.get())
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }

}
