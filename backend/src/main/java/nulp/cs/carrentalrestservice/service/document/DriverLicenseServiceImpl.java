package nulp.cs.carrentalrestservice.service.document;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.DriverLicense;
import nulp.cs.carrentalrestservice.entity.DriverLicenseCategory;
import nulp.cs.carrentalrestservice.mapper.DriverLicenseMapper;
import nulp.cs.carrentalrestservice.model.dto.DriverLicenseCategoryDTO;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import nulp.cs.carrentalrestservice.repository.DriverLicensesRepository;
import nulp.cs.carrentalrestservice.repository.LicenseCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DriverLicenseServiceImpl implements DriverLicenseService {
    private final DriverLicenseMapper driverLicenseMapper;
    private final DriverLicensesRepository driverLicensesRepository;
    private final LicenseCategoryRepository licenseCategoryRepository;
    @Override
    public DriverLicensesDTO createDriverLicense(DriverLicensesDTO driverLicensesDTO) {
        DriverLicense license = driverLicenseMapper.driverLicensesDtoToDriverLicenses(driverLicensesDTO);
        DriverLicense saved = driverLicensesRepository
                .save(license);
        Set<DriverLicenseCategory> categories = license.getCategories();
        for (DriverLicenseCategory category : categories) {
            category.setLicense(saved);
        }

        licenseCategoryRepository.saveAll(categories);

        return driverLicenseMapper
                .driverLicensesToDriverLicensesDto(driverLicensesRepository.save(saved));
    }

    @Override
    public Optional<DriverLicensesDTO> getDriverLicenseById(UUID id) {
        return Optional.ofNullable(driverLicenseMapper
                .driverLicensesToDriverLicensesDto(driverLicensesRepository
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
