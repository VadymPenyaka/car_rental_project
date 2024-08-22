package nulp.cs.carrentalrestservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderDTO {
    private long id;
    private AdminDTO admin;
    private OrderStatus status;
    private CustomerDTO customer;
    private OrderDetailDTO orderDetail;
}
