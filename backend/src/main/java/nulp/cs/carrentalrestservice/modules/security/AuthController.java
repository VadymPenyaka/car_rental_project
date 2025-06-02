package nulp.cs.carrentalrestservice.modules.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.dto.response.LoginResult;
import nulp.cs.carrentalrestservice.shared.dto.request.LoginRequest;
import nulp.cs.carrentalrestservice.modules.person.PersonDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.UpdatePersonRequest;
import nulp.cs.carrentalrestservice.shared.dto.response.LoginResponse;
import nulp.cs.carrentalrestservice.modules.person.PersonDetails;
import nulp.cs.carrentalrestservice.modules.person.PersonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping(AuthController.BASE_PATH)
public class AuthController {
    public static final String BASE_PATH = "/api/v1/auth";
    private final PersonService personService;
    private final AuthService authService;

    @GetMapping
    public PersonDTO getAuthenticatedPersonInfo (@AuthenticationPrincipal PersonDetails personDetails) {
        return personService.getPersonById(personDetails.person().getId())
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPerson (@RequestBody LoginRequest loginForm) {
        LoginResult loginResult = authService.authenticateUser(loginForm);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", loginResult.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();

        LoginResponse response = LoginResponse.builder()
                .role(loginResult.role())
                .token(loginResult.accessToken())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@CookieValue(value = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing!");
        }

        return new ResponseEntity<>(authService.refreshAccessToken(refreshToken), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePersonInfo (@Valid @RequestBody UpdatePersonRequest personRequest, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        personService.updatePerson(personRequest);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return new ResponseEntity<>(HttpStatus.valueOf(301));
    }
}
