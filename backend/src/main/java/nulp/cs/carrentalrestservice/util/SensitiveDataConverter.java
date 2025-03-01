package nulp.cs.carrentalrestservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Converter
@Component
public class SensitiveDataConverter implements AttributeConverter<String, String> {
    private final CaesarCipherServiceImpl caesarCipherService;

    public SensitiveDataConverter(CaesarCipherServiceImpl caesarCipherService) {
        this.caesarCipherService = caesarCipherService;
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        return caesarCipherService.encrypt(s);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return caesarCipherService.decrypt(s);
    }
}
