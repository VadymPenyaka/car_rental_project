package nulp.cs.carrentalrestservice.controller;

import jakarta.transaction.Transactional;
import nulp.cs.carrentalrestservice.entity.Location;
import nulp.cs.carrentalrestservice.mapper.LocationMapper;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import nulp.cs.carrentalrestservice.repository.CarRepository;
import nulp.cs.carrentalrestservice.repository.LocationRepository;
import nulp.cs.carrentalrestservice.service.LocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = "/init_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class LocationControllerIT {
    @Autowired
    private LocationController locationController;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationMapper locationMapper;

    @Test
    @Rollback
    @Transactional
    void createLocation() {
        LocationDTO dto = LocationDTO.builder()
                .locationName("test")
                .region("Region")
                .city("City")
                .address("Address")
                .latitude("324234234")
                .longitude("324234234")
                .build();

        ResponseEntity response = locationController.createLocation(dto);
        Long size = locationRepository.count();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(size).isEqualTo(3);
    }

    @Test
    void getLocationById() {
        Location location = locationRepository.findAll().get(0);

        LocationDTO locationDTO = locationController.getLocationById(location.getId());

        assertThat(locationDTO.getLocationName()).isEqualTo(location.getLocationName());
    }

    @Test
    @Rollback
    @Transactional
    void updateLocationBuId() {
        Location location = locationRepository.findAll().get(0);
        LocationDTO locationDTO = locationMapper.locationToLocationDto(location);

        LocationDTO updated = LocationDTO.builder()
                    .locationName("updated")
                    .build();

        ResponseEntity response = locationController.updateLocationBuId(location.getId(), updated);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(locationDTO.getLocationName()).isNotEqualTo(updated.getLocationName());
    }

    @Test
    @Rollback
    @Transactional
    void deleteLocationById() {
        Location location = locationRepository.findAll().get(0);

        ResponseEntity response = locationController.deleteLocationById(location.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(locationRepository.findById(location.getId())).isEqualTo(Optional.empty());
    }
}