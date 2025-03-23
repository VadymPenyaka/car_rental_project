package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.DriverLicense;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DriverLicenseMapper {
    @Mapping(source = "document", target = "document")
    DriverLicense driverLicensesDtoToDriverLicenses (DriverLicensesDTO driverLicensesDTO);
    @Mapping(source = "document", target = "document")
    DriverLicensesDTO driverLicensesToDriverLIcensesDto (DriverLicense driverLicense);
}
