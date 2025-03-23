package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.model.dto.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {
    @Mapping(source = "person", target = "person")
    Customer customerDtoToCustomer (CustomerDTO customerDTO);

    @Mapping(source = "person", target = "person")
    CustomerDTO customerToCustomerDto (Customer customer);
}
