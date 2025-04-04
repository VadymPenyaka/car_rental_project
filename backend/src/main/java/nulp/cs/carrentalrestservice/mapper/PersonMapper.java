package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Person;
import nulp.cs.carrentalrestservice.model.dto.PersonDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
    Person personDtoToPerson (PersonDTO personDTO);
    PersonDTO personToPersonDto (Person person);
}
