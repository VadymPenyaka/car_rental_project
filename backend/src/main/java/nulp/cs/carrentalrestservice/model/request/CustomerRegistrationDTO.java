package nulp.cs.carrentalrestservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegistrationDTO {
    private UUID id;
    private String firstName;
    private String sureName;
    private String email;
    private String password;
    private String phoneNumber;
}
