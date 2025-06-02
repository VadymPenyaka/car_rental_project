package nulp.cs.carrentalrestservice.modules;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.serivce.BankIdVerificationService;
import nulp.cs.carrentalrestservice.modules.order.dto.CarOrderDTO;
import nulp.cs.carrentalrestservice.shared.dto.request.CustomerRegistrationRequest;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonalInfoRequest;
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

//    TODO move to customer auth controller
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

//        customerInfoService.registerCustomer(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//todo create another controller for customer data divide for admin and customer
    @GetMapping("/{id}/orders")
    @PreAuthorize("#id==authentication.principal.id or hasRole('ADMIN')")
    public List<CarOrderDTO> getAllCustomerOrders (@PathVariable UUID id) {
//        return customerInfoService.getAllCustomerOrders(id);
        return null;
    }


}
