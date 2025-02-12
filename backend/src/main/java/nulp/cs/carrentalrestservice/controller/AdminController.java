package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.model.LoginForm;
import nulp.cs.carrentalrestservice.service.AdminService;
import nulp.cs.carrentalrestservice.security.CustomUserDetailsService;
import nulp.cs.carrentalrestservice.security.JwtService;
import nulp.cs.carrentalrestservice.util.LoggingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController {
    public static final String BASE_PATH = "/api/v1/admins";
    private final AdminService adminService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

//TODO send part of data
    @GetMapping(BASE_PATH)
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdminDTO> getAllAdmins () {
        return adminService.getAllAdmins();
    }

    @PostMapping(BASE_PATH)
//    @PreAuthorize("hasRole(T(nulp.cs.carrentalrestservice.model.enumeration.Role).SYS_ADMIN.name())")
    public ResponseEntity<?> createAdmin (@RequestBody AdminDTO admin) {
        adminService.createAdmin(admin);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    @PostAuthorize("returnObject.email == authentication.name")
    public AdminDTO getAdminById (@PathVariable UUID id) {
        return adminService.getAdminById(id).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(BASE_PATH +"/{id}")
    @PreAuthorize("hasRole(T(nulp.cs.carrentalrestservice.model.enumeration.Role).SYS_ADMIN.name())")
    public ResponseEntity<?> deleteAdminById (@PathVariable UUID id) {
        if(!adminService.deleteAdminByID(id))
            throw new NotFoundException();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BASE_PATH +"/{id}")
//    @PreAuthorize("@adminServiceImpl.isOwner(#id, authentication.name) or " +
//            "hasRole(T(nulp.cs.carrentalrestservice.model.enumeration.Role).SYS_ADMIN.name())")
    public ResponseEntity<?> updateAdminById (@PathVariable UUID id, @RequestBody AdminDTO adminDTO) {
        if (adminService.updateAdminById(id, adminDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BASE_PATH+"/authenticate")
    public ResponseEntity<?> authenticateAdmin (@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));

        if(authentication.isAuthenticated()) {
            return new ResponseEntity<>(jwtService.generateToken(customUserDetailsService
                    .loadUserByUsername(loginForm.username())), HttpStatus.OK);
        }
        else
            throw new UsernameNotFoundException("Invalid credentials");
    }

}
