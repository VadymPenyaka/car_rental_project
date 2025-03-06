package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.request.LoginRequest;
import nulp.cs.carrentalrestservice.model.PersonDTO;
import nulp.cs.carrentalrestservice.model.response.LoginResponse;
import nulp.cs.carrentalrestservice.security.CustomUserDetailsService;
import nulp.cs.carrentalrestservice.security.JwtService;
import nulp.cs.carrentalrestservice.security.PersonDetails;
import nulp.cs.carrentalrestservice.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthController.BASE_PATH)
public class AuthController {
    public static final String BASE_PATH = "/api/v1/auth";
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public PersonDTO getAuthenticatedPersonInfo (@AuthenticationPrincipal PersonDetails personDetails) {
        return personService.getPersonById(personDetails.person().getId())
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPerson (@RequestBody LoginRequest loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = customUserDetailsService
                    .loadUserByUsername(loginForm.username());

            String token = jwtService.generateToken(userDetails);

            String role = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElse("USER");

            LoginResponse response = LoginResponse.builder()
                    .role(role)
                    .token(token)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("Invalid credentials!");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id==authentication.principal.person.id")
    public ResponseEntity<?> updatePersonInfo (@PathVariable UUID id, @Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        if (personService.updatePersonById(id, personDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
