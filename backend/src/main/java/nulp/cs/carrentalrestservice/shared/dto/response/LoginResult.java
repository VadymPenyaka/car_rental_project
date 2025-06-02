package nulp.cs.carrentalrestservice.shared.dto.response;

public record LoginResult(String accessToken, String refreshToken, String role) {}