package nulp.cs.carrentalrestservice.service.document;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.PassportMapper;
import nulp.cs.carrentalrestservice.model.dto.PassportDTO;
import nulp.cs.carrentalrestservice.repository.PassportRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    private final PassportMapper passportMapper;

    @Override
    public PassportDTO createPassport(PassportDTO passportDTO) {
        return passportMapper.passportToPassportDto(passportRepository
                .save(passportMapper.passportDtoToPassport(passportDTO)));
    }

    @Override
    public Optional<PassportDTO> getPassportByCustomerId(UUID customerId) {
        return Optional.ofNullable(passportMapper
                .passportToPassportDto(passportRepository
                        .findByCustomerId(customerId)));
    }

    @Override
    public Optional<PassportDTO> getPassportById(UUID id) {
        return Optional.ofNullable(passportMapper.passportToPassportDto(passportRepository.findById(id).orElse(null)));
    }

    @Override
    public Optional<PassportDTO> getDtoFromImage(String url) {
        return Optional.empty();
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return passportRepository.existsByDocumentNumber(documentNumber);
    }
}
