package nulp.cs.carrentalrestservice.modules.person;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.event.VerificationEmailEvent;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.shared.exception.VerificationTokenExpiredException;
import nulp.cs.carrentalrestservice.modules.security.VerificationType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationService {
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PersonPendingConfirmationRepository repository;


    public void createToken (PersonDTO person, VerificationType type, String email) {

        PersonPendingConfirmation confirmation = PersonPendingConfirmation.builder()
                .data(person)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .type(type)
                .username(email)
                .build();

        PersonPendingConfirmation saved = repository.save(confirmation);

        applicationEventPublisher.publishEvent(
                new VerificationEmailEvent(this, saved.getId(), email, type)
        );
    }

    public PersonPendingConfirmation confirmAndGet (UUID id) {
        PersonPendingConfirmation confirmation = repository.findById(id)
                .orElseThrow(()-> new NotFoundException("Token not found"));

        if (confirmation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new VerificationTokenExpiredException("URL is expired");
        }

        repository.deleteById(confirmation.getId());

        return confirmation;
    }

}
