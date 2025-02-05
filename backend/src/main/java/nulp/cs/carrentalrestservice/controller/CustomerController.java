package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.model.CustomerPersonalInfoDTO;
import nulp.cs.carrentalrestservice.model.LoginForm;
import nulp.cs.carrentalrestservice.security.CustomUserDetailsService;
import nulp.cs.carrentalrestservice.service.CustomerService;
import nulp.cs.carrentalrestservice.security.JwtService;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
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

    @PreAuthorize("@customerServiceImpl.isOwner(#id, authentication.name)")
    @GetMapping(BASE_PATH +"/{id}")
    public CustomerDTO getCustomerById (@PathVariable UUID id) {
        return customerService.getCustomerByID(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BASE_PATH)
    public ResponseEntity<?> createCustomer (@Valid @RequestBody CustomerDTO customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        customerService.createCustomer(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    TODO make accessible for right user
    @PostMapping(BASE_PATH+"/personalInfo")
    @PreAuthorize("@customerServiceImpl.isOwner(#id, #customerPersonalInfoDTO.passportId)")
    public ResponseEntity<?> setCustomerPersonalInfo (@PathVariable UUID id, @Valid @RequestBody CustomerPersonalInfoDTO customerPersonalInfoDTO) {


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(BASE_PATH +"/{id}")
    @PreAuthorize("@customerServiceImpl.isOwner(#id, #customerDTO.email)")
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
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        else
            throw new UsernameNotFoundException("Invalid credentials");
    }


}
