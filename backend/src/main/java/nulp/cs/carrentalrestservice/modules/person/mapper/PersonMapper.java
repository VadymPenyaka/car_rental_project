package nulp.cs.carrentalrestservice.modules.person.mapper;

import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;
import nulp.cs.carrentalrestservice.modules.person.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person personDtoToPerson (PersonDTO personDTO);
    PersonDTO personToPersonDto (Person person);

}
