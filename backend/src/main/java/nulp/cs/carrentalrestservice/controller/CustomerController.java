package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.LoginForm;
import nulp.cs.carrentalrestservice.service.CustomUserDetailsService;
import nulp.cs.carrentalrestservice.service.CustomerService;
import nulp.cs.carrentalrestservice.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static final String BASE_PATH = "/api/v1/customers";
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    @GetMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public CustomerDTO getCustomerById (@PathVariable UUID id) {
        return customerService.getCustomerByID(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BASE_PATH)
    @PreAuthorize("permitAll()")
    public ResponseEntity createCustomer (@Valid @RequestBody CustomerDTO customer) {
        customerService.createCustomer(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(BASE_PATH +"/{id}")
    public ResponseEntity updateCustomerById (@PathVariable UUID id,@Valid @RequestBody CustomerDTO customerDTO) {
        if(customerService.updateCustomerById(id, customerDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping(BASE_PATH+"/authenticate")
    public ResponseEntity authenticateCustomer (@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if(authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(customUserDetailsService
                    .loadUserByUsername(loginForm.username())), HttpStatus.NO_CONTENT);
        }
        else
            throw new UsernameNotFoundException("Invalid credentials");
    }


}
