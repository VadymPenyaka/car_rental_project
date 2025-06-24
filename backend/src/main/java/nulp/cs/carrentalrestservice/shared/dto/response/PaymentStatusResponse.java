package nulp.cs.carrentalrestservice.shared.dto.response;

public record PaymentStatusResponse (
    String status,
    String message,
    String sessionId
){}
