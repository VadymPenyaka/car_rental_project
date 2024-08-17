package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {
    public static final String ADMIN_BASE_PATH = "/api/v1/admins";
    private final AdminService adminService;

    @GetMapping(ADMIN_BASE_PATH)
    public List<AdminDTO> getAllAdmins () {
       return adminService.getAllAdmins();
    }

    @PutMapping(ADMIN_BASE_PATH)
    public ResponseEntity createAdmin (@RequestBody AdminDTO admin) {
        adminService.createAdmin(admin);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(ADMIN_BASE_PATH+"/{id}")
    public AdminDTO getAdminById (@PathVariable Long id) {
        return adminService.getAdminById(id).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(ADMIN_BASE_PATH+"/{id}")
    public ResponseEntity deleteAdminById (@PathVariable Long id) {
        if(!adminService.deleteAdminByID(id))
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(ADMIN_BASE_PATH+"/{id}")
    public ResponseEntity updateAdminById (@PathVariable Long id, @RequestBody AdminDTO adminDTO) {
        if (adminService.updateAdminById(id, adminDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

//    @GetMapping(ADMIN_BASE_PATH_V2+"/withFewestOrders")
//    public AdminDTO getAdminWithFewestOrders () {
//        return adminService.getAdminWithFewestOrders().orElseThrow(NotFoundException::new);
//    }

}
