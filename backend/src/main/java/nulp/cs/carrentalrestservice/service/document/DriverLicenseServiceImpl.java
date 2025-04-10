package nulp.cs.carrentalrestservice.service.document;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.DriverLicenseMapper;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import nulp.cs.carrentalrestservice.repository.DriverLicensesRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DriverLicenseServiceImpl implements DriverLicenseService {
    private final DriverLicenseMapper driverLicenseMapper;
    private final DriverLicensesRepository driverLicensesRepository;
    @Override
    public DriverLicensesDTO createDriverLicense(DriverLicensesDTO driverLicensesDTO) {
        return driverLicenseMapper.driverLicensesToDriverLIcensesDto(driverLicensesRepository
                .save(driverLicenseMapper.driverLicensesDtoToDriverLicenses(driverLicensesDTO)));
    }

    @Override
    public Optional<DriverLicensesDTO> getDriverLicenseById(UUID id) {
        return Optional.ofNullable(driverLicenseMapper
                .driverLicensesToDriverLIcensesDto(driverLicensesRepository
                        .findById(id).orElse(null)));
    }

    @Override
    public Optional<DriverLicensesDTO> getDtoFromImage(String url) {
        return Optional.empty();
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return driverLicensesRepository.existsByDocumentNumber(documentNumber);
    }
}
