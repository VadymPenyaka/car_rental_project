package nulp.cs.carrentalrestservice.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(UserDetails personDetails);

    String generateRefreshToken(UserDetails personDetails);

    String extractUsername(String jwt);

    boolean isTokenValid(String token, UserDetails user);

    boolean isTokenExpired(String token);
}
