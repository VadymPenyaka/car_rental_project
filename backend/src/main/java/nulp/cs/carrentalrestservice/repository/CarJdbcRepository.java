package nulp.cs.carrentalrestservice.repository;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.entity.*;
import nulp.cs.carrentalrestservice.model.enumeration.*;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CarJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<Car> getAllCarsByCriteria (CarSearchRequest carSearchRequest) {
        StringBuilder sql = new StringBuilder("SELECT c.*, m.*, l.*, cp.* " +
                "FROM car c " +
                "LEFT JOIN car_schedule s ON c.id = s.car_id " +
                "LEFT JOIN model m ON c.model_id = m.id " +
                "LEFT JOIN location l ON c.location_id = l.id " +
                "LEFT JOIN brand b ON m.brand_name = b.name " +
                "LEFT JOIN car_pricing cp ON c.car_pricing_id = cp.id " +
                "WHERE 1=1 ");

        MapSqlParameterSource source = new MapSqlParameterSource();

        if (carSearchRequest.getCity() != null) {
            sql.append("AND l.city = :city ");
            source.addValue("city", carSearchRequest.getCity());
        }
        if (carSearchRequest.getCarClass() != null) {
            sql.append("AND c.car_class = :carClass ");
            source.addValue("carClass", carSearchRequest.getCarClass().name());
        }
        if (carSearchRequest.getFuelType() != null) {
            sql.append("AND c.fuel_type =  :fuelType");
            source.addValue("fuelType", carSearchRequest.getFuelType().name());
        }
        if (carSearchRequest.getGearboxType() != null) {
            sql.append("AND c.gearbox_type = :gearboxType ");
            source.addValue("gearboxType", carSearchRequest.getGearboxType().name());
        }
        if (carSearchRequest.getMaxPrice() != null) {
            sql.append("AND cp.more_then_month <= :maxPrice ");
            source.addValue("maxPrice", carSearchRequest.getMaxPrice());
        }
        if (carSearchRequest.getMinPrice() != null) {
            sql.append("AND cp.more_then_month >= :minPrice ");
            source.addValue("minPrice", carSearchRequest.getMinPrice());
        }
        if (carSearchRequest.getBrand() != null) {
            sql.append("AND b.name = :brand ");
            source.addValue("brand", carSearchRequest.getBrand());
        }

        if (carSearchRequest.getStartDate() != null && carSearchRequest.getEndDate() != null) {
            sql.append("AND NOT EXISTS (SELECT 1 FROM car_schedule s2 " +
                    "WHERE s2.car_id = c.id " +
                    "AND s2.end_date >= :startDate " +
                    "AND s2.start_date <= :endDate) ");
            source.addValue("startDate", Date.valueOf(carSearchRequest.getStartDate()));
            source.addValue("endDate", Date.valueOf(carSearchRequest.getEndDate()));
        }

        System.out.println(sql);
        System.out.println(source);
        return jdbcTemplate.query(sql.toString(), source, carRowMapper());
    }

    private RowMapper<Car> carRowMapper () {
        return (rs, rowNum) -> {
            Brand brand = Brand.builder()
                    .name(rs.getString("brand_name"))
                    .build();

            Model model = Model.builder()
                    .id(UUID.fromString(rs.getString("model_id")))
                    .modelName(rs.getString("model_name"))
                    .year(rs.getInt("year"))
                    .description(rs.getString("description"))
                    .brandName(brand)
                    .build();

            Location location = Location.builder()
                    .id(UUID.fromString(rs.getString("location_id")))
                    .locationName(rs.getString("location_name"))
                    .region(rs.getString("region"))
                    .city(rs.getString("city"))
                    .address(rs.getString("address"))
                    .latitude(rs.getString("latitude"))
                    .longitude(rs.getString("longitude"))
                    .build();

            CarPricing pricing = CarPricing.builder()
                    .id(UUID.fromString(rs.getString("car_pricing_id")))
                    .pledge(rs.getDouble("pledge"))
                    .upToThreeDays(rs.getDouble("up_to_three_days"))
                    .upToTenDays(rs.getDouble("up_to_ten_days"))
                    .upToMonth(rs.getDouble("up_to_month"))
                    .moreThenMonth(rs.getDouble("more_then_month"))
                    .build();

            return Car.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .vin(rs.getString("vin"))
                    .bodyType(BodyType.valueOf(rs.getString("body_type")))
                    .number(rs.getString("number"))
                    .color(rs.getString("color"))
                    .carClass(CarClass.valueOf(rs.getString("car_class")))
                    .fuelConsumption(rs.getInt("fuel_consumption"))
                    .numberOfSeats(rs.getInt("number_of_seats"))
                    .fuelType(FuelType.valueOf(rs.getString("fuel_type")))
                    .gearboxType(GearboxType.valueOf(rs.getString("gearbox_type")))
                    .driveType(DriveType.valueOf(rs.getString("drive_type")))
                    .engineCapacity(rs.getDouble("engine_capacity"))
                    .fuelTankCapacity(rs.getInt("fuel_tank_capacity"))
                    .trunkCapacity(rs.getInt("trunk_capacity"))
                    .licenseCategory(LicenseCategory.valueOf(rs.getString("license_category")))
                    .requiredExperience(rs.getInt("required_experience"))
                    .model(model)
                    .location(location)
                    .carPricing(pricing)
                    .build();
        };
    }
}
