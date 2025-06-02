package nulp.cs.carrentalrestservice.modules.car.serivce;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.enity.Brand;
import nulp.cs.carrentalrestservice.modules.car.enity.Model;
import nulp.cs.carrentalrestservice.modules.car.mapper.BrandMapper;
import nulp.cs.carrentalrestservice.modules.car.mapper.ModelMapper;
import nulp.cs.carrentalrestservice.modules.car.dto.ModelDTO;
import nulp.cs.carrentalrestservice.modules.car.repository.ModelRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ModelService {
    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;
    private final BrandMapper brandMapper;
    private final BrandService brandService;

    public ModelDTO createIfNotExist (ModelDTO modelDTO) {
        Model model = modelMapper.modelDtoToModel(modelDTO);

        if (!modelRepository.existsByModelNameAndBrandName(model.getModelName(), model.getBrandName())) {
            Brand newBrand = brandMapper.brandDtoToBrand(brandService.createIfNotExist(modelDTO.getBrandName()));
            model.setBrandName(newBrand);
            return modelMapper.modelToModelDto(modelRepository.saveAndFlush(model));
        }

        return modelMapper.modelToModelDto(modelRepository
                .findByModelNameAndBrandName(model.getModelName(), model.getBrandName()));
    }
}
