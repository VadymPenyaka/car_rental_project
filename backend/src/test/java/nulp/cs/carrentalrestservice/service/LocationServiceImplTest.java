package nulp.cs.carrentalrestservice.service;

import nulp.cs.carrentalrestservice.entity.Location;
import nulp.cs.carrentalrestservice.mapper.LocationMapper;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationMapper locationMapper;
    @InjectMocks
    private LocationServiceImpl locationService;
    private Location location;
    private LocationDTO locationDTO;

    @BeforeEach
    void setUp() {
        location = Location.builder()
                .locationName("test")
                .region("North America")
                .city("City")
                .address("Address 33")
                .longitude("123")
                .latitude("321")
                .build();

        locationDTO = LocationDTO.builder()
                .locationName("test")
                .region("North America")
                .city("City")
                .address("Address 33")
                .longitude("123")
                .latitude("321")
                .build();

    }

    @Test
    void createLocation() {
        when(locationRepository.save(any())).thenReturn(location);
        when(locationMapper.locationToLocationDto(any())).thenReturn(locationDTO);

        LocationDTO result = locationService.createLocation(locationDTO);

        assertThat(result).isEqualTo(locationDTO);
    }

    @Test
    void getLocationByID() {
        when(locationRepository.findById(any())).thenReturn(Optional.of(location));
        when(locationMapper.locationToLocationDto(location)).thenReturn(locationDTO);

        LocationDTO result = locationService.getLocationByID(location.getId()).get();

        assertThat(result).isEqualTo(locationDTO);
    }

    @Test
    void deleteLocationById() {
        when(locationRepository.existsById(any())).thenReturn(true);

        boolean result = locationService.deleteLocationById(location.getId());

        verify(locationRepository).deleteById(location.getId());
        assertThat(result).isTrue();
    }

    @Test
    void updateLocationById() {
        LocationDTO expected = LocationDTO.builder()
                .locationName("test")
                .region("North America")
                .city("City")
                .address("Address 33")
                .longitude("123")
                .latitude("321")
                .build();

        when(locationRepository.findById(any())).thenReturn(Optional.of(location));
        location.setLocationName(expected.getLocationName());
        when(locationRepository.save(any())).thenReturn(location);
        when(locationMapper.locationToLocationDto(location)).thenReturn(expected);

        LocationDTO result = locationService.updateLocationById(location.getId(), expected).get();

        assertThat(result).isEqualTo(expected);
    }
}