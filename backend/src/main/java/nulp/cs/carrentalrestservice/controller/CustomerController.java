package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CustomerDTO;
import nulp.cs.carrentalrestservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static final String BASE_PATH = "/api/v1/customers";
    private final CustomerService customerService;

    @GetMapping(BASE_PATH +"/{id}")
    public CustomerDTO getCustomerById (@PathVariable Long id) {
        return customerService.getCustomerByID(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BASE_PATH)
    public ResponseEntity createCustomer (@Valid @RequestBody CustomerDTO customer) {
        customerService.createCustomer(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(BASE_PATH +"/{id}")
    public ResponseEntity updateCustomerById (@PathVariable Long id,@Valid @RequestBody CustomerDTO customerDTO) {
        if(customerService.updateCustomerById(id, customerDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(BASE_PATH +"/findByName")
    public List<CustomerDTO> getCustomersBySureName(@RequestParam String lastName) {
        return customerService.getCustomersBySureName(lastName);
    }

}
