package nulp.cs.carrentalrestservice.service.person;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.VerificationToken;
import nulp.cs.carrentalrestservice.event.VerificationEmailEvent;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.exception.VerificationTokenExpiredException;
import nulp.cs.carrentalrestservice.mapper.VerificationTokenMapper;
import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import nulp.cs.carrentalrestservice.model.dto.VerificationTokenDTO;
import nulp.cs.carrentalrestservice.model.enumeration.VerificationType;
import nulp.cs.carrentalrestservice.repository.VerificationTokenRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {
    private final VerificationTokenRepository repository;
    private final VerificationTokenMapper mapper;
    private final ApplicationEventPublisher applicationEventPublisher;


    public void createToken (PersonDTO person, String value, VerificationType type) {
        VerificationTokenDTO verificationToken = VerificationTokenDTO.builder()
                .type(type)
                .value(value)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .person(person)
                .build();

        VerificationTokenDTO savedToken = mapper
                .toDto(repository.save(mapper.toEntity(verificationToken)));
        System.out.println(savedToken);
        String recipientEmail;
        if (savedToken.getType() == VerificationType.NEW_EMAIL) {
            recipientEmail=value;
        } else {
            recipientEmail = person.getUsername();
        }

        applicationEventPublisher.publishEvent(
                new VerificationEmailEvent(this, savedToken.getToken(), recipientEmail, type)
        );
    }

    public VerificationTokenDTO verifyAndGetToken (UUID token) {
        VerificationToken foundToken = repository.findById(token)
                .orElseThrow(() -> new NotFoundException("Token not found"));

        if (foundToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new VerificationTokenExpiredException("Verification token is expired");
        }
        if (foundToken.getType()==VerificationType.EMAIL) {
            createToken(mapper.toDto(foundToken).getPerson(), foundToken.getValue(), VerificationType.NEW_EMAIL);
        }

        repository.deleteById(token);

        return mapper.toDto(foundToken);
    }

}
