package nulp.cs.carrentalrestservice.modules.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VerificationTokenController.BASE_PATH)
@RequiredArgsConstructor
public class VerificationTokenController {
    public final static String BASE_PATH = "/api/v1/verify";
    private final PersonService personService;

    @GetMapping("/change")
    public ResponseEntity<?> verifyCredentialsChange(@RequestParam String token) {
        personService.verifyUpdate(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/registration")
    public ResponseEntity<?> verifyRegistration (@RequestParam String token) {
        personService.verifyUpdate(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
