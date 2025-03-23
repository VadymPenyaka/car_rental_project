package nulp.cs.carrentalrestservice.service.location;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.LocationMapper;
import nulp.cs.carrentalrestservice.model.dto.LocationDTO;
import nulp.cs.carrentalrestservice.repository.LocationRepository;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;
    private final LoggingService loggingService;

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        loggingService.logInfo("Creating location with name: " + locationDTO.getLocationName());
        return mapper.locationToLocationDto(repository.save(mapper
                .locationDtoToLocation(locationDTO)));
    }

    @Override
    public Optional<LocationDTO> getLocationByID(UUID id) {
        loggingService.logInfo("Getting location with ID: " + id);
        return Optional.ofNullable(mapper.locationToLocationDto(repository
                .findById(id).orElse(null)));
    }

    @Override
    public boolean deleteLocationById(UUID id) {
        loggingService.logInfo("Deleting location with ID: " + id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            loggingService.logInfo("Location deleted successfully");
            return true;
        }
        loggingService.logInfo("Location not found for ID: " + id);
        return false;
    }

    @Override
    public Optional<LocationDTO> updateLocationById(UUID id, LocationDTO locationDTO) {
        loggingService.logInfo("Updating location with ID: " + id);
        AtomicReference<Optional<LocationDTO>> atomicReference = new AtomicReference<>();

        repository.findById(id).ifPresentOrElse(foundLocation -> {
                    foundLocation.setLocationName(locationDTO.getLocationName());
                    foundLocation.setRegion(locationDTO.getRegion());
                    foundLocation.setCity(locationDTO.getCity());
                    foundLocation.setAddress(locationDTO.getAddress());
                    foundLocation.setLatitude(locationDTO.getLatitude());
                    foundLocation.setLongitude(locationDTO.getLongitude());

                    atomicReference.set(Optional.ofNullable(mapper
                            .locationToLocationDto(repository.save(foundLocation))));
                    loggingService.logInfo("Location updated successfully");
                },()-> {
                    atomicReference.set(Optional.empty());
                    loggingService.logInfo("Location not found for ID: " + id);
                }
        );

        return atomicReference.get();
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        loggingService.logInfo("Getting all locations");
        return repository.findAll().stream()
                .map(mapper::locationToLocationDto).toList();
    }
}
