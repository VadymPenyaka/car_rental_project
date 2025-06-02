package nulp.cs.carrentalrestservice.modules.location.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.exception.NotFoundException;
import nulp.cs.carrentalrestservice.modules.location.dto.LocationDTO;
import nulp.cs.carrentalrestservice.modules.location.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(LocationAdminController.BASE_PATH)
public class LocationAdminController {
    public final static String BASE_PATH = "admins/locations";
    private final LocationService locationService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createLocation (@RequestBody LocationDTO locationDTO) {
        locationService.createLocation(locationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLocationBuId (@PathVariable UUID id, @RequestBody LocationDTO locationDTO) {
        if (locationService.updateLocationById(id, locationDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocationById (@PathVariable UUID id) {
        if (!locationService.deleteLocationById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
