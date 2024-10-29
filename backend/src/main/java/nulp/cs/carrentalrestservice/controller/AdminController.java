package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.AdminDTO;
import nulp.cs.carrentalrestservice.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminController {
    public static final String BASE_PATH = "/api/v1/admins";
    private final AdminService adminService;

    @GetMapping(BASE_PATH)
    public List<AdminDTO> getAllAdmins () {
       return adminService.getAllAdmins();
    }

    @PutMapping(BASE_PATH)
    public ResponseEntity createAdmin (@RequestBody AdminDTO admin) {
        adminService.createAdmin(admin);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(BASE_PATH +"/{id}")
    public AdminDTO getAdminById (@PathVariable UUID id) {
        return adminService.getAdminById(id).orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(BASE_PATH +"/{id}")
    public ResponseEntity deleteAdminById (@PathVariable UUID id) {
        if(!adminService.deleteAdminByID(id))
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BASE_PATH +"/{id}")
    public ResponseEntity updateAdminById (@PathVariable UUID id, @RequestBody AdminDTO adminDTO) {
        if (adminService.updateAdminById(id, adminDTO).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
