package nulp.cs.carrentalrestservice.config;

import nulp.cs.carrentalrestservice.enumConverter.FuelTypeStringToEnumConverter;
import nulp.cs.carrentalrestservice.enumConverter.GearboxTypeStringToEnumConverter;
import nulp.cs.carrentalrestservice.enumConverter.StatusStringToEnumConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnumConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StatusStringToEnumConverter());
        registry.addConverter(new FuelTypeStringToEnumConverter());
        registry.addConverter(new GearboxTypeStringToEnumConverter());
    }
}
