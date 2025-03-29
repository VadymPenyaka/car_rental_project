package nulp.cs.carrentalrestservice.service.document;

import nulp.cs.carrentalrestservice.model.dto.PassportDTO;

import java.util.Optional;
import java.util.UUID;

public interface PassportService {
    PassportDTO createPassport (PassportDTO passportDTO);

    Optional<PassportDTO> getPassportByCustomerId (UUID customerId);

    Optional<PassportDTO> getPassportById (UUID id);

    Optional<PassportDTO> getDtoFromImage (String url);

    boolean existsByDocumentNumber (String documentNumber);
}
