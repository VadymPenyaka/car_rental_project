package nulp.cs.carrentalrestservice.validation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.model.dto.CarDTO;
import nulp.cs.carrentalrestservice.service.car.CarService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class CarValidator implements Validator {

    private final CarService carService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return CarDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        CarDTO carDTO = (CarDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vin", "field.required", "VIN is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "field.required", "Color is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "model", "field.required", "Model is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gearboxType", "field.required", "Gearbox type is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "field.required", "Number is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bodyType", "field.required", "Body type is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "carClass", "field.required", "Car class is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fuelType", "field.required", "Fuel type is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "location", "field.required", "Location is required");

        if (carDTO.getRegistrationInfo().getVin() != null && !carDTO.getRegistrationInfo().getVin().matches("[A-HJ-NPR-Z0-9]{17}")) {
            errors.rejectValue("vin", "field.invalid", "VIN must be a valid 17-character string");
        }

        if (carService.isVinUsed(carDTO.getRegistrationInfo().getVin())) {
            errors.rejectValue("vin", "field.invalid", "Car with this VIN is already exists");
        }

        if (carDTO.getCarDetails().getNumberOfSeats() <= 0) {
            errors.rejectValue("numberOfSeats", "field.invalid", "Number of seats must be greater than 0");
        }

        if (carDTO.getCarDetails().getTrunkCapacity() < 0) {
            errors.rejectValue("trunkCapacity", "field.invalid", "Trunk capacity must be non-negative");
        }

        if (carDTO.getCarDetails().getFuelConsumption() < 0) {
            errors.rejectValue("fuelConsumption", "field.invalid", "Fuel consumption must be non-negative");
        }

        if (carDTO.getCarDetails().getFuelTankCapacity() < 0) {
            errors.rejectValue("fuelTankCapacity", "field.invalid", "Fuel tank capacity must be non-negative");
        }

        if (carDTO.getCarDetails().getEngineCapacity() <= 0) {
            errors.rejectValue("engineCapacity", "field.invalid", "Engine capacity must be greater than 0");
        }

        if (carDTO.getCarDetails().getRequiredExperience() < 0) {
            errors.rejectValue("requiredExperience", "field.invalid", "Required experience must be non-negative");
        }
    }
}
