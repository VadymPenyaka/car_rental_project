package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(LocationController.BASE_PATH)
public class LocationController {
    public static final String BASE_PATH = "/api/v1/locations";

    private final LocationService locationService;

    @GetMapping
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }


    @GetMapping("/{id}")
    public LocationDTO getLocationById (@PathVariable UUID id) {
        return locationService.getLocationByID(id)
                .orElseThrow(NotFoundException::new);
    }


}
