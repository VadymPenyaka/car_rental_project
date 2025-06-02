package nulp.cs.carrentalrestservice.modules.location.service;

import nulp.cs.carrentalrestservice.modules.location.dto.LocationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface LocationService {
    void createLocation (LocationDTO locationDTO);

    Optional<LocationDTO> getLocationByID (UUID id);

    boolean deleteLocationById (UUID id);

    Optional<LocationDTO> updateLocationById(UUID id, LocationDTO locationDTO);

    List<LocationDTO> getAllLocations();
}
