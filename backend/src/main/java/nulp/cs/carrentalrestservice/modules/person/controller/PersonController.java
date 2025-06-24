package nulp.cs.carrentalrestservice.modules.person.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.person.dto.PersonDTO;
import nulp.cs.carrentalrestservice.modules.person.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PersonController.BASE_PATH)
@RequiredArgsConstructor
public class PersonController {
    public final static String BASE_PATH = "/api/v1/person";
    public final PersonService personService;

    @GetMapping
    public ResponseEntity<?> getPersonInfo() {
        PersonDTO personDTO = personService.getAuthenticatedPerson();
        personDTO.setPassword("**************");
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
}
