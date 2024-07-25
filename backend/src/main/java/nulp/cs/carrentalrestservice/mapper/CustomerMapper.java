package nulp.cs.carrentalrestservice.mapper;

import nulp.cs.carrentalrestservice.entity.Customer;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer (CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto (Customer customer);
}
