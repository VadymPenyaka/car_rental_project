package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.service.customer.CustomerService;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.dto.CustomerDTO;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.util.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(CustomerController.BASE_PATH)
public class CustomerController {
    public static final String BASE_PATH = "/api/v1/customers";
    private final CustomerService customerService;

//    @PreAuthorize("@customerServiceImpl.isOwner(#id, authentication.name)")
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById (@PathVariable UUID id) {
        return customerService.getCustomerByID(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        customerService.registerCustomer(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/personalInfo")
    public ResponseEntity<?> createCustomer (@Valid @RequestBody CustomerDTO customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        customerService.createCustomerFullInfo(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id==authentication.principal.id")
    public ResponseEntity<?> updateCustomerById (@PathVariable UUID id,@Valid @RequestBody CustomerDTO customerDTO) {
        if(customerService.updateCustomerById(id, customerDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("#id==authentication.principal.id")
    public List<CarOrderDTO> getAllCustomerOrders (@PathVariable UUID id) {
        return customerService.getAllCustomerOrders(id);
    }


}
