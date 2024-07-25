package nulp.cs.carrentalrestservice.util;

import nulp.cs.carrentalrestservice.model.FuelType;
import org.springframework.core.convert.converter.Converter;

public class FuelTypeStringToEnumConverter implements Converter<String, FuelType> {
    @Override
    public FuelType convert(String source) {
        return FuelType.valueOf(source.toUpperCase());
    }
}
