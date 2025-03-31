package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.DriverLicense;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DriverLicenseMapper {
    DriverLicense driverLicensesDtoToDriverLicenses (DriverLicensesDTO driverLicensesDTO);
    DriverLicensesDTO driverLicensesToDriverLIcensesDto (DriverLicense driverLicense);
}
