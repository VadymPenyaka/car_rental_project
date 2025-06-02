package nulp.cs.carrentalrestservice.modules.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.person.Person;
import nulp.cs.carrentalrestservice.modules.person.PersonDetails;
import nulp.cs.carrentalrestservice.modules.person.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<Person> personOptional = personRepository.findByUsername(username);
        System.out.println();
        if (personOptional.isPresent()) {
            return PersonDetails.builder()
                    .person(personOptional.get())
                    .build();
        }
        throw new UsernameNotFoundException("Invalid credentials!");
    }

}
