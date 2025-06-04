package nulp.cs.carrentalrestservice.modules.person.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.person.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonController {
    public final static String BASE_PATH = "/api/v1/person";
    public final PersonService personService;

    @GetMapping
    public ResponseEntity<?> getPersonInfo() {
        return new ResponseEntity<>(personService.getAuthenticatedPerson(), HttpStatus.OK);
    }
}
