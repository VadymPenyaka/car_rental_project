package nulp.cs.carrentalrestservice.modules.person.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import nulp.cs.carrentalrestservice.modules.person.PersonDTO;

@Converter
public class PersonDTOConverter implements AttributeConverter<PersonDTO, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(PersonDTO personDTO) {
        if (personDTO == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(personDTO);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing PersonDTO", e);
        }
    }

    @Override
    public PersonDTO convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData, PersonDTO.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing PersonDTO", e);
        }
    }
}
