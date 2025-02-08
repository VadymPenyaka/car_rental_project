package nulp.cs.carrentalrestservice.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(PersonDetails personDetails);

    String extractUsername(String jwt);

    boolean isTokenValid(String token, PersonDetails user);
}
