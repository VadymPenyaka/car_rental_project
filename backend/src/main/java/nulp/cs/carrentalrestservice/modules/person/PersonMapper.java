package nulp.cs.carrentalrestservice.modules.person;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person personDtoToPerson (PersonDTO personDTO);
    PersonDTO personToPersonDto (Person person);
}
