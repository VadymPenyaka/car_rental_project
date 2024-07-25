package nulp.cs.carrentalrestservice.util;

import nulp.cs.carrentalrestservice.model.OrderStatus;
import org.springframework.core.convert.converter.Converter;

public class StatusStringToEnumConverter implements Converter<String, OrderStatus> {

    @Override
    public OrderStatus convert(String source) {
        return OrderStatus.valueOf(source.toUpperCase());
    }
}
