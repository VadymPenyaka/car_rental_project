package nulp.cs.carrentalrestservice.util;

import nulp.cs.carrentalrestservice.model.GearboxType;
import org.springframework.core.convert.converter.Converter;

public class GearboxTypeStringToEnumConverter implements Converter<String, GearboxType> {
    @Override
    public GearboxType convert(String source) {
        return GearboxType.valueOf(source.toUpperCase());
    }
}
