package nulp.cs.carrentalrestservice.modules.location.mapper;

import nulp.cs.carrentalrestservice.modules.location.enity.Location;
import nulp.cs.carrentalrestservice.modules.location.dto.LocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location locationDtoToLocation (LocationDTO locationDTO);

    LocationDTO locationToLocationDto (Location location);
}
