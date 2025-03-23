package nulp.cs.carrentalrestservice.controller.admin;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.util.DataGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys_admin/data")
@RequiredArgsConstructor
public class UpdateDataForTestController {
    private final DataGeneratorService dataGeneratorService;
    @PostMapping
    public ResponseEntity<?> generateCars (@RequestParam Integer size) {
        dataGeneratorService.generateCars(size);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
