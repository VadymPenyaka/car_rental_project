package nulp.cs.carrentalrestservice.security;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.service.CarOrderService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CarOrderPermissionEvaluator {
    private final CarOrderService carOrderService;

    public boolean isOwner (UUID orderId, Object principal) {
        if (principal instanceof UserDetails) {

            return carOrderService.isOwner(orderId,  ((UserDetails) principal).getUsername());
        }
        return false;
    }
}
