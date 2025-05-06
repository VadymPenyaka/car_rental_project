package nulp.cs.carrentalrestservice.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.InvalidRefreshTokenException;
import nulp.cs.carrentalrestservice.model.dto.LoginResult;
import nulp.cs.carrentalrestservice.model.request.LoginRequest;
import nulp.cs.carrentalrestservice.model.response.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public LoginResult authenticateUser(LoginRequest loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.username(), loginForm.password()
                ));

        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }

        UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(loginForm.username());
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails);

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("USER");

        return new LoginResult(accessToken, refreshToken, role);
    }

    public LoginResponse refreshAccessToken(String refreshToken) {
        UserDetails userDetails = customUserDetailsService
                .loadUserByUsername(jwtService.extractUsername(refreshToken));

        String newToken = refreshTokenService.refreshAccessToken(refreshToken, userDetails)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token is expired or invalid!"));

        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("USER");

        return LoginResponse.builder()
                .role(role)
                .token(newToken)
                .build();
    }
}
