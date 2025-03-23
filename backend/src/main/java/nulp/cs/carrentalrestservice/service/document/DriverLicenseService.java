package nulp.cs.carrentalrestservice.service.document;

import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;

import java.util.Optional;
import java.util.UUID;

public interface DriverLicenseService {
    DriverLicensesDTO createDriverLicense (DriverLicensesDTO driverLicensesDTO);

    Optional<DriverLicensesDTO> getDriverLicenseById (UUID id);

    Optional<DriverLicensesDTO> getDtoFromImage (String url);

    boolean existsByDocumentNumber (String documentNumber);
}
