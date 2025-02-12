package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.LoginForm;
import nulp.cs.carrentalrestservice.model.PersonDTO;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(AuthController.BASE_PATH)
@RequiredArgsConstructor
public class AuthController {
    public static final String BASE_PATH = "/api/v1/auth";
    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/public" + BASE_PATH)
    public PersonDTO getAuthenticatedPersonInfo (@AuthenticationPrincipal PersonDetails personDetails) {
        return personService.getPersonById(personDetails.person().getId())
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/public" + BASE_PATH +"/register")
    public ResponseEntity<?> registerPerson (@Valid @RequestBody PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        personService.createPerson(personDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/public" + BASE_PATH +"/login")
    public ResponseEntity<?> loginPerson (@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(customUserDetailsService
                    .loadUserByUsername(loginForm.username()));

            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("Invalid credentials!");
        }
    }

    @PutMapping
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
