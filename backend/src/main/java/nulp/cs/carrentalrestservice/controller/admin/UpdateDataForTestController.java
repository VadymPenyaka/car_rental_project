package nulp.cs.carrentalrestservice.controller.admin;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.mapper.CarMapper;
import nulp.cs.carrentalrestservice.service.car.CarService;
import nulp.cs.carrentalrestservice.util.CarGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sys_admin/data")
@RequiredArgsConstructor
public class UpdateDataForTestController {
    private final CarService carService;
    private final CarGenerator carGenerator;
    private final CarMapper carMapper;

    @PostMapping("/{size}")
    public ResponseEntity<?> generateCars (@PathVariable Integer size, @RequestParam MultipartFile[] multipartFiles) {
        for (int i=0; i<size; i++) {
            carService.createCar(carMapper.carToCarDto(carGenerator.generateCar()), multipartFiles);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
