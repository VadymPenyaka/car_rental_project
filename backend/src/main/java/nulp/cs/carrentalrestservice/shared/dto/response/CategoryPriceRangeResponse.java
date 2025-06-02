package nulp.cs.carrentalrestservice.shared.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.dto.CarClass;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash("categoryPriceRangeResponse")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPriceRangeResponse implements Serializable {
    private Double min;
    private Double max;
    private CarClass carClass;
}
