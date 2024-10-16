package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.LocationDTO;

import java.util.Optional;
import java.util.UUID;


public interface LocationService {
    LocationDTO createLocation (LocationDTO locationDTO);

    Optional<LocationDTO> getLocationByID (UUID id);

    boolean deleteLocationById (UUID id);

    Optional<LocationDTO> updateLocationById(UUID id, LocationDTO locationDTO);
}
