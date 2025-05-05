package nulp.cs.carrentalrestservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String SECRET = "9RAOB5cMJIVWn1g8uw1erTVkFngeE/OVKKRBGmQBCq234swu+T8waHQBhawDGbmmkkmIj0KpGS1SkzoW9P/HjQ==";
    private static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(30);

    @Override
    public String generateToken(UserDetails personDetails) {
        return Jwts.builder()
                .subject(personDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails personDetails) {
        return Jwts.builder()
                .subject(personDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String extractUsername(String jwt) {
        return getClaim(jwt, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Base64
                .getDecoder().decode(SECRET));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T getClaim (String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(jwt);

        return claimsResolver.apply(claims);
    }
}
