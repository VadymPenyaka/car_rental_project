package nulp.cs.carrentalrestservice.modules.car.mapper;

import nulp.cs.carrentalrestservice.modules.car.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.modules.car.dto.CarClass;
import nulp.cs.carrentalrestservice.modules.car.dto.DriveType;
import nulp.cs.carrentalrestservice.modules.car.dto.FuelType;
import nulp.cs.carrentalrestservice.modules.car.dto.GearboxType;
import nulp.cs.carrentalrestservice.shared.dto.response.CarCardResponse;
import nulp.cs.carrentalrestservice.shared.dto.response.CategoryPriceRangeResponse;
import org.springframework.jdbc.core.RowMapper;

import java.util.UUID;

public class CarRowMapper {
    public static final RowMapper<CarCardResponse> carCardResponseMapper = (rs, rowNum) -> CarCardResponse.builder()
            .id(UUID.fromString(rs.getString("id")))
            .modelName(rs.getString("model_name"))
            .brandName(rs.getString("brand_name"))
            .numberOfSeats(rs.getInt("number_of_seats"))
            .fuelType(FuelType.valueOf(rs.getString("fuel_type")))
            .fuelConsumption(rs.getInt("fuel_consumption"))
            .driveType(DriveType.valueOf(rs.getString("drive_type")))
            .engineCapacity(rs.getDouble("engine_capacity"))
            .gearboxType(GearboxType.valueOf(rs.getString("gearbox_type")))
            .carPricing(CarPricingDTO.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .upToThreeDays(rs.getDouble("up_to_three_days"))
                    .upToTenDays(rs.getDouble("up_to_ten_days"))
                    .upToMonth(rs.getDouble("up_to_month"))
                    .moreThenMonth(rs.getDouble("more_then_month"))
                    .pledge(rs.getDouble("pledge"))
                    .build())
            .build();

    public static final RowMapper<CategoryPriceRangeResponse> categoryPriceRangeMapper = (rs, rowNum) -> {
        CategoryPriceRangeResponse response = new CategoryPriceRangeResponse();
        response.setMin(rs.getDouble("min_price"));
        response.setMax(rs.getDouble("max_price"));

        String carClassString = rs.getString("car_class");
        if (carClassString != null) {
            response.setCarClass(CarClass.valueOf(carClassString));
        }

        return response;
    };

}
