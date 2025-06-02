package nulp.cs.carrentalrestservice.modules.car.controller;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.modules.car.serivce.BrandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BrandController.BASE_PATH)
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    public final static String BASE_PATH = "/api/v1/brands";

    @GetMapping("/available")
    public List<String> getAllAvailableBrands () {
        return brandService.getAllAvailableBrands();
    }
}
