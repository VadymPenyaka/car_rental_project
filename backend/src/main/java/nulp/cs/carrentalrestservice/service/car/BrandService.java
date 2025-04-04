package nulp.cs.carrentalrestservice.service.car;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.mapper.BrandMapper;
import nulp.cs.carrentalrestservice.model.dto.BrandDTO;
import nulp.cs.carrentalrestservice.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandDTO createIfNotExist (BrandDTO brandDTO) {
        if (!brandRepository.existsById(brandDTO.getName())) {
            return brandMapper.brandToBrandDto(brandRepository
                    .saveAndFlush(brandMapper.brandDtoToBrand(brandDTO)));
        }

        return brandMapper.brandToBrandDto(brandRepository.findById(brandDTO.getName())
                .orElseThrow((() -> new NotFoundException("Brand not found!"))));
    }

    public List<BrandDTO> getAllBrands () {
        return brandRepository.findAll().stream()
                .map(brandMapper::brandToBrandDto).toList();
    }

    public boolean deleteBrandByName (String name) {
        if(brandRepository.existsById(name)) {
            brandRepository.deleteById(name);
            return true;
        }

        return false;
    }
}
