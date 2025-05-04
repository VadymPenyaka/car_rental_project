package nulp.cs.carrentalrestservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.model.request.CustomerFullInfoRequest;
import nulp.cs.carrentalrestservice.service.person.customer.BankIdService;
import nulp.cs.carrentalrestservice.service.person.customer.CustomerService;
import nulp.cs.carrentalrestservice.model.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.model.request.CustomerRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(CustomerController.BASE_PATH)
public class CustomerController {
    public static final String BASE_PATH = "/api/v1/customers";
    private final CustomerService customerService;
    private final BankIdService bankIdService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        customerService.registerCustomer(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/personalInfo")
    public ResponseEntity<?> addCustomerPersonalInfo(@Valid @RequestBody CustomerFullInfoRequest customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        customerService.createCustomerFullInfo(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/personalInfo/bank_id")
    public ResponseEntity<?> addCustomerPersonalBankIdInfo() {
        CustomerFullInfoRequest customer = bankIdService.getDataFromApi();
        customerService.createCustomerFullInfo(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/orders")
    @PreAuthorize("#id==authentication.principal.id or hasRole('ADMIN')")
    public List<CarOrderDTO> getAllCustomerOrders (@PathVariable UUID id) {
        return customerService.getAllCustomerOrders(id);
    }


}
