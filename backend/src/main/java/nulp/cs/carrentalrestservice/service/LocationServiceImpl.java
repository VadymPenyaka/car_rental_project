package nulp.cs.carrentalrestservice.service;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.LocationMapper;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;
    private final LocationMapper mapper;

    @Override
    public LocationDTO createLocation(LocationDTO locationDTO) {
        return mapper.locationToLocationDto(repository.save(mapper
                .locationDtoToLocation(locationDTO)));
    }

    @Override
    public Optional<LocationDTO> getLocationByID(Long id) {
        return Optional.ofNullable(mapper.locationToLocationDto(repository
                .findById(id).orElse(null)));
    }

    @Override
    public boolean deleteLocationById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<LocationDTO> updateLocationById(Long id, LocationDTO locationDTO) {
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
                },()-> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }
}
