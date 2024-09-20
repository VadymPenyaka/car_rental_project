package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LocationController {
    public static final String BASE_PATH = "api/v1/locations";

    private final LocationService locationService;

    @PostMapping(BASE_PATH)
    public ResponseEntity createLocation (@RequestBody LocationDTO locationDTO) {
        locationService.createLocation(locationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    public LocationDTO getLocationById (@PathVariable Long id) {
        return locationService.getLocationByID(id)
                .orElseThrow(NotFoundException::new);
    }

    @PutMapping(BASE_PATH +"/{id}")
    public ResponseEntity updateLocationBuId (@PathVariable Long id, @RequestBody LocationDTO locationDTO) {
        if (locationService.updateLocationById(id, locationDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_PATH +"/{id}")
    public ResponseEntity deleteLocationById (@PathVariable Long id) {
        if (!locationService.deleteLocationById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
