package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.DriverLicense;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import org.mapstruct.*;

@Mapper
public interface DriverLicenseMapper {
    DriverLicense driverLicensesDtoToDriverLicenses(DriverLicensesDTO dto);

    DriverLicensesDTO driverLicensesToDriverLicensesDto(DriverLicense entity);
}
