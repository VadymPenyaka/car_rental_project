package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Location;
import nulp.cs.carrentalrestservice.model.LocationDTO;
import org.mapstruct.Mapper;

@Mapper
public interface LocationMapper {
    Location locationDtoToLocation (LocationDTO locationDTO);

    LocationDTO locationToLocationDto (Location location);
}
