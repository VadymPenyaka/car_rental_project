package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class LocationController {
    public static final String BASE_PATH = "/api/v1/locations";

    private final LocationService locationService;

//    TODO Add getAll method
    @PostMapping(BASE_PATH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createLocation (@RequestBody LocationDTO locationDTO) {
        locationService.createLocation(locationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    public LocationDTO getLocationById (@PathVariable UUID id) {
        return locationService.getLocationByID(id)
                .orElseThrow(NotFoundException::new);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(BASE_PATH +"/{id}")
    public ResponseEntity updateLocationBuId (@PathVariable UUID id, @RequestBody LocationDTO locationDTO) {
        if (locationService.updateLocationById(id, locationDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(BASE_PATH +"/{id}")
    public ResponseEntity deleteLocationById (@PathVariable UUID id) {
        if (!locationService.deleteLocationById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
