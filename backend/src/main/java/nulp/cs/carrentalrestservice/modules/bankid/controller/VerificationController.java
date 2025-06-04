package nulp.cs.carrentalrestservice.modules.bankid.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.bankid.serivce.BankIdVerificationService;
import nulp.cs.carrentalrestservice.shared.dto.request.PersonalInfoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(VerificationController.BASE_PATH)
public class VerificationController {
    public static final String BASE_PATH = "/api/v1/verification";
    private final BankIdVerificationService bankIdVerificationService;

    @PostMapping("/customer-data")
    public ResponseEntity<?> addCustomerPersonalInfo(@Valid @RequestBody PersonalInfoRequest customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        bankIdVerificationService.createCustomerFullInfo(customer);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/mock-data")
    public ResponseEntity<?> addCustomerPersonalBankIdInfo() {

        bankIdVerificationService.createRandomPersonalData();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
