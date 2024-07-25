package nulp.cs.carrentalrestservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarOrderDTO {
    private long id;
    private AdminDTO admin;
    private OrderStatus status;
    private OrderDetailDTO orderDetail;
}
