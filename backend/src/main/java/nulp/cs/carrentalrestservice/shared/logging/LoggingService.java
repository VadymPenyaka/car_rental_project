package nulp.cs.carrentalrestservice.shared.logging;

public interface LoggingService {
    void logInfo(String message);
    void logDebug(String message);
    void logError(String message, Throwable exception);
}
