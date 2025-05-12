package nulp.cs.carrentalrestservice.repository;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CarRowMapper;
import nulp.cs.carrentalrestservice.model.enumeration.*;
import nulp.cs.carrentalrestservice.model.request.CarSearchRequest;
import nulp.cs.carrentalrestservice.model.response.CarCardResponse;
import nulp.cs.carrentalrestservice.model.response.CategoryPriceRangeResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class CarJdbcRepository {
    private final NamedParameterJdbcTemplate jdbcNamedTemplate;
    private final JdbcTemplate jdbcTemplate;

    public List<CarCardResponse> getAllCarsByCriteria (CarSearchRequest carSearchRequest) {
        return jdbcNamedTemplate.query(buildQueryCriteriaQuery(carSearchRequest), buildSource(carSearchRequest), CarRowMapper.carCardResponseMapper);
    }

    public String buildQueryCriteriaQuery (CarSearchRequest request) {
        StringBuilder sql = new StringBuilder("SELECT c.*, m.*, l.*, cp.*, cd.* " +
                "FROM car c " +
                "LEFT JOIN car_schedule s ON c.id = s.car_id " +
                "LEFT JOIN model m ON c.model_id = m.id " +
                "LEFT JOIN location l ON c.location_id = l.id " +
                "LEFT JOIN brand b ON m.brand_name = b.name " +
                "LEFT JOIN car_pricing cp ON c.car_pricing_id = cp.id " +
                "LEFT JOIN car_details cd ON c.car_details_id = cd.id " +
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


    public List<CategoryPriceRangeResponse> getCategoriesPriceRange() {
        String sql = """
        SELECT c.car_class AS car_class,
               MIN(p.more_then_month) AS min_price,
               MAX(p.more_then_month) AS max_price
        FROM car c
        LEFT JOIN car_pricing p ON c.car_pricing_id = p.id
        GROUP BY c.car_class
        """;

        Map<CarClass, CategoryPriceRangeResponse> resultMap = new EnumMap<>(CarClass.class);

        jdbcNamedTemplate.query(sql, rs -> {
            Optional.ofNullable(rs.getString("car_class"))
                    .map(CarClass::valueOf)
                    .ifPresent(carClass -> {
                        try {
                            resultMap.put(carClass, CategoryPriceRangeResponse.builder()
                                    .carClass(carClass)
                                    .min(rs.getDouble("min_price"))
                                    .max(rs.getDouble("max_price"))
                                    .build());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
        });

        Arrays.stream(CarClass.values())
                .forEach(carClass -> resultMap.putIfAbsent(carClass,
                        CategoryPriceRangeResponse.builder()
                                .carClass(carClass)
                                .min(0.0)
                                .max(0.0)
                                .build()));

        return new ArrayList<>(resultMap.values());
    }

    public boolean existsByVin(String vin) {
        String query = "SELECT COUNT(*) FROM car_registration_info WHERE vin = ?";

        Integer count = jdbcTemplate.queryForObject(query, new Object[] { vin }, Integer.class);

        return count != null && count > 0;
    }
}
