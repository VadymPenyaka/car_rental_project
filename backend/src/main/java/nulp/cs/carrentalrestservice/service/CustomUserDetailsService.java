package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.Admin;
import nulp.cs.carrentalrestservice.repository.AdminRepository;
import nulp.cs.carrentalrestservice.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        TODO
//        Optional<Customer> customerOptional = customerRepository.findByEmail(username);
//        if (customerOptional.isPresent()) {
//            Customer customer = customerOptional.get();
//            return new CustomUserDetails(customer.getEmail(), "Add password to customer");
//        }
//
        Optional<Admin> adminOptional = adminRepository.findAdminByEmail(username);
        if(adminOptional.isPresent()) {
            Admin admin = adminOptional.get();

            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles("ADMIN").build();
        }


        throw new UsernameNotFoundException("User not found");
    }
}
