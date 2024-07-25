package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.Exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_BASE_PATH_V1 = "/api/v1/customers";
    public static final String CUSTOMER_BASE_PATH_V2 = "/api/v2/customers";
    private final CustomerService customerService;

    @GetMapping(CUSTOMER_BASE_PATH_V1+"/{id}")
    public CustomerDTO getCustomerById (@PathVariable Long id) {
        return customerService.getCustomerByID(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(CUSTOMER_BASE_PATH_V1)
    public ResponseEntity createCustomer (@RequestBody CustomerDTO customer) {
        customerService.createCustomer(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(CUSTOMER_BASE_PATH_V1+"/{id}")
    public ResponseEntity updateCustomerById (@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        if(customerService.updateCustomerById(id, customerDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(CUSTOMER_BASE_PATH_V2+"/findByName")
    public List<CustomerDTO> getCustomersByLastName (@RequestParam String lastName) {
        return customerService.getCustomersByLastName(lastName);
    }


}
