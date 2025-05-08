package nulp.cs.carrentalrestservice.repository;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.model.dto.CarPricingDTO;
import nulp.cs.carrentalrestservice.model.enumeration.*;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CategoryPriceRangeResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
//TODO refactor move mappers to external class
@Repository
@RequiredArgsConstructor
public class CarJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<CarCardResponse> getAllCarsByCriteria (CarSearchRequest carSearchRequest) {
        return jdbcTemplate.query(buildQueryCriteriaQuery(carSearchRequest), buildSource(carSearchRequest), carCardResponseMapper());
    }

    public String buildQueryCriteriaQuery (CarSearchRequest request) {
        StringBuilder sql = new StringBuilder("SELECT c.*, m.*, l.*, cp.* " +
                "FROM car c " +

                "LEFT JOIN car_schedule s ON c.id = s.car_id " +
                "LEFT JOIN model m ON c.model_id = m.id " +
                "LEFT JOIN location l ON c.location_id = l.id " +
                "LEFT JOIN brand b ON m.brand_name = b.name " +
                "LEFT JOIN car_pricing cp ON c.car_pricing_id = cp.id " +
                "WHERE 1=1 ");

        if (request.getCity() != null) {
            sql.append("AND l.city = :city ");
        }
        if (request.getCarClass() != null) {
            sql.append("AND c.car_class = :carClass ");
        }
        if (request.getFuelType() != null) {
            sql.append("AND c.fuel_type =  :fuelType");
        }
        if (request.getGearboxType() != null) {
            sql.append("AND c.gearbox_type = :gearboxType ");
        }
        if (request.getMaxPrice() != null) {
            sql.append("AND cp.more_then_month <= :maxPrice ");
        }
        if (request.getMinPrice() != null) {
            sql.append("AND cp.more_then_month >= :minPrice ");
        }
        if (request.getBrand() != null) {
            sql.append("AND b.name = :brand ");
        }

        if (request.getStartDate() != null && request.getEndDate() != null) {
            sql.append("AND NOT EXISTS (SELECT 1 FROM car_schedule s2 " +
                    "WHERE s2.car_id = c.id " +
                    "AND s2.end_date >= :startDate " +
                    "AND s2.start_date <= :endDate) ");
        }

        return sql.toString();
    }

    public MapSqlParameterSource buildSource (CarSearchRequest request) {
        MapSqlParameterSource source = new MapSqlParameterSource();

        if (request.getCity() != null) {
            source.addValue("city", request.getCity());
        }
        if (request.getCarClass() != null) {
            source.addValue("carClass", request.getCarClass().name());
        }
        if (request.getFuelType() != null) {
            source.addValue("fuelType", request.getFuelType().name());
        }
        if (request.getGearboxType() != null) {
            source.addValue("gearboxType", request.getGearboxType().name());
        }
        if (request.getMaxPrice() != null) {
            source.addValue("maxPrice", request.getMaxPrice());
        }
        if (request.getMinPrice() != null) {
            source.addValue("minPrice", request.getMinPrice());
        }
        if (request.getBrand() != null) {
            source.addValue("brand", request.getBrand());
        }
        if (request.getStartDate() != null && request.getEndDate() != null) {
            source.addValue("startDate", Date.valueOf(request.getStartDate()));
            source.addValue("endDate", Date.valueOf(request.getEndDate()));
        }

        return source;
    }

    private RowMapper<CarCardResponse> carCardResponseMapper() {
        return (rs, rowNum) -> CarCardResponse.builder()
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
    }

//TODO refactor with rs
    public List<CategoryPriceRangeResponse> getCategoriesPriceRange() {
        String sql = "SELECT MIN(p.more_then_month) AS min_price, " +
                "MAX(p.more_then_month) AS max_price, " +
                "c.car_class AS car_class " +
                "FROM car c " +
                "JOIN car_pricing p ON c.car_pricing_id = p.id " +
                "GROUP BY c.car_class";

        RowMapper<CategoryPriceRangeResponse> rowMapper = new CategoryPriceRangeMapper();

        return jdbcTemplate.query(sql, rowMapper);
    }

    private static class CategoryPriceRangeMapper implements RowMapper<CategoryPriceRangeResponse> {
        @Override
        public CategoryPriceRangeResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
            CategoryPriceRangeResponse response = new CategoryPriceRangeResponse();

            response.setMin(rs.getDouble("min_price"));
            response.setMax(rs.getDouble("max_price"));

            String carClassString = rs.getString("car_class");
            if (carClassString != null) {
                response.setCarClass(CarClass.valueOf(carClassString));
            }

            return response;
        }
    }

}
