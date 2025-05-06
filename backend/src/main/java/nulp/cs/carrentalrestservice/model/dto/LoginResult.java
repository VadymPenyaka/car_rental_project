package nulp.cs.carrentalrestservice.model.dto;

public record LoginResult(String accessToken, String refreshToken, String role) {}