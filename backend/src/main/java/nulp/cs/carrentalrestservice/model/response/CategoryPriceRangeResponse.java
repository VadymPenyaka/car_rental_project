package nulp.cs.carrentalrestservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nulp.cs.carrentalrestservice.model.enumeration.CarClass;
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
