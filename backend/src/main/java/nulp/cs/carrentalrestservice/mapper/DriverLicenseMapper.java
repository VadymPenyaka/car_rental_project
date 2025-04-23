package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.DriverLicense;
import nulp.cs.carrentalrestservice.entity.DriverLicenseCategory;
import nulp.cs.carrentalrestservice.model.dto.DriverLicenseCategoryDTO;
import nulp.cs.carrentalrestservice.model.dto.DriverLicensesDTO;
import nulp.cs.carrentalrestservice.model.enumeration.LicenseCategory;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface DriverLicenseMapper {
    DriverLicense driverLicensesDtoToDriverLicenses(DriverLicensesDTO dto);

    DriverLicensesDTO driverLicensesToDriverLicensesDto(DriverLicense entity);
}
