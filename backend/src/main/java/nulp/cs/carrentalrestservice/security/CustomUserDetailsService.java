package nulp.cs.carrentalrestservice.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.entity.Person;
import nulp.cs.carrentalrestservice.model.enumeration.Role;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import nulp.cs.carrentalrestservice.repository.PersonRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public PersonDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Person> personOptional = personRepository.findByUsername(username);

        if (personOptional.isPresent()) {
            return PersonDetails.builder()
                    .person(personOptional.get())
                    .build();
        }

//        Optional<Customer> customerOptional = customerRepository.findByEmail(username);
//
//        if (customerOptional.isPresent()) {
//            Customer customer = customerOptional.get();
//
//            return User.builder()
//                    .username(customer.getEmail())
//                    .password(customer.getPassword())
//                    .roles(Role.USER.name())
//                    .build();
//        }
//
//        Optional<Admin> adminOptional = adminRepository.findAdminByEmail(username);
//        if(adminOptional.isPresent()) {
//            Admin admin = adminOptional.get();
//            return User.builder()
//                    .username(admin.getEmail())
//                    .password(admin.getPassword())
//                    .roles(admin.getRole().name()).build();
//        }


        throw new UsernameNotFoundException("User not found");
    }

}
