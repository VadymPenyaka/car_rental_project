package nulp.cs.carrentalrestservice.service.location;

import nulp.cs.carrentalrestservice.model.dto.LocationDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface LocationService {
    LocationDTO createLocation (LocationDTO locationDTO);

    Optional<LocationDTO> getLocationByID (UUID id);

    boolean deleteLocationById (UUID id);

    Optional<LocationDTO> updateLocationById(UUID id, LocationDTO locationDTO);

    List<LocationDTO> getAllLocations();
}
