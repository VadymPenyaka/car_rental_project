package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.DriverLicensesDTO;

import java.util.Optional;
import java.util.UUID;

public interface DriverLicenseService {
    DriverLicensesDTO createDriverLicense (DriverLicensesDTO driverLicensesDTO);

    Optional<DriverLicensesDTO> getDriverLicenseById (UUID id);

    Optional<DriverLicensesDTO> getDtoFromImage (String url);

    boolean existsByDocumentNumber (String documentNumber);
}
