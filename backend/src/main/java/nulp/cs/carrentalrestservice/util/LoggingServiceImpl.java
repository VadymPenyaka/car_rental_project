package nulp.cs.carrentalrestservice.util;

import lombok.AllArgsConstructor;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class LoggingServiceImpl implements LoggingService {
    private final Logger logger = LoggerFactory.getLogger(LoggingServiceImpl.class);

    @Override
    public void logInfo(String message) {
        logger.info("{} - [{}#{}] {}", Instant.now(), getClassName(), getMethodName(), message);
    }

    @Override
    public void logDebug(String message) {
        logger.debug("{} - [{}#{}] {}", Instant.now(), getClassName(), getMethodName(), message);
    }

    @Override
    public void logError(String message, Throwable exception) {
        logger.error("{} - [{}#{}] {} - Exception: {}", Instant.now(), getClassName(), getMethodName(), message, exception.getMessage(), exception);
    }

    private String getClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            if (!className.startsWith("nulp.cs.carrentalrestservice.util")) { // Замість "com.example.logging" вкажіть пакет вашого логера
                return stackTrace[i].getClassName();
            }
        }
        return "Unknown class";
    }

    private String getMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            if (!className.startsWith("nulp.cs.carrentalrestservice.util")) { // Замість "com.example.logging" вкажіть пакет вашого логера
                return stackTrace[i].getMethodName();
            }
        }
        return "Unknown method";
    }
}
