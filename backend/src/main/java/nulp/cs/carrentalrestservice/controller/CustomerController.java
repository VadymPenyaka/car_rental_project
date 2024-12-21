package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.LoginForm;
import nulp.cs.carrentalrestservice.security.CustomUserDetailsService;
import nulp.cs.carrentalrestservice.service.CustomerService;
import nulp.cs.carrentalrestservice.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
    @PostAuthorize("returnObject.email == authentication.name")
    public CustomerDTO getCustomerById (@PathVariable UUID id) {
        return customerService.getCustomerByID(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BASE_PATH)
    public ResponseEntity createCustomer (@Valid @RequestBody CustomerDTO customer) {
        customerService.createCustomer(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO update
    @PutMapping(BASE_PATH +"/{id}")
//    @PostAuthorize("returnObject.email == authentication.name")
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
            String token =  jwtService.generateToken(customUserDetailsService
                    .loadUserByUsername(loginForm.username()));
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        }
        else
            throw new UsernameNotFoundException("Invalid credentials");
    }


}
