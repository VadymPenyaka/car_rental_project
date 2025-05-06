package nulp.cs.carrentalrestservice.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.RefreshToken;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.RefreshTokenMapper;
import nulp.cs.carrentalrestservice.model.dto.RefreshTokenDTO;
import nulp.cs.carrentalrestservice.repository.RefreshTokenRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtService jwtService;
    private final RefreshTokenMapper tokenMapper;
    private final RefreshTokenRepository repository;

    public String createRefreshToken(UserDetails personDetails) {
        String refreshToken = jwtService.generateRefreshToken(personDetails);

        return tokenMapper.entityToDto(repository.save(RefreshToken.builder()
                        .refreshToken(refreshToken)
                        .username(personDetails.getUsername())
                .build())).getRefreshToken();
    }

    public void deleteToken (String token) {
        repository.deleteByRefreshToken(token);
    }

    public RefreshTokenDTO getByToken (String token) {
        return tokenMapper.entityToDto(repository.findByRefreshToken(token)
                .orElseThrow(()-> new NotFoundException("Token not found!")));
    }



    public Optional<String> refreshAccessToken(String refreshToken, UserDetails userDetails) {
        RefreshTokenDTO foundToken = getByToken(refreshToken);
        String tokenValue = foundToken.getRefreshToken();

        if (jwtService.isTokenExpired(tokenValue)) {
            deleteToken(tokenValue);
            return Optional.empty();
        }

        if (!userDetails.getUsername().equals(foundToken.getUsername())) {
            throw new AccessDeniedException("Invalid refresh token owner");
        }


        String newAccessToken = jwtService.generateAccessToken(userDetails);
        return Optional.of(newAccessToken);
    }
}
