package nulp.cs.carrentalrestservice.service.car;

import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CarCustomerDetailsResponse;
import nulp.cs.carrentalrestservice.model.response.CategoryPriceRangeResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CarService {
    void createCar (CarDTO carDTO, MultipartFile[] files);

    List<CategoryPriceRangeResponse> getCarCategoriesPriceRanges();

    List<CarCardResponse> getAllCarsByCriteria(CarSearchRequest carDto);

    List<CarCardResponse> getAll();

    Boolean deleteCarById (UUID id);

    Optional<CarDTO> updateCarByID(UUID id, CarDTO carDTO);

    Optional<CarCustomerDetailsResponse> getCarCustomerDetailsById(UUID id);

    Optional<CarDTO> getCarFullDetailsById(UUID id);

    boolean isVinUsed (String vin);
}
