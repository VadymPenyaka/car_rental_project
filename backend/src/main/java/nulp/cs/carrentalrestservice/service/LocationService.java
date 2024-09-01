package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.model.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.LocationDTO;

import java.util.Optional;


public interface LocationService {
    LocationDTO createLocation (LocationDTO locationDTO);

    Optional<LocationDTO> getLocationByID (Long id);

    boolean deleteLocationById (Long id);

    Optional<LocationDTO> updateLocationById(Long id, LocationDTO locationDTO);
}
