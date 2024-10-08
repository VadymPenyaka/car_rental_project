package nulp.cs.carrentalrestservice.controller;

import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import nulp.cs.carrentalrestservice.exception.NotFoundException;
import nulp.cs.carrentalrestservice.model.CarScheduleDTO;
import nulp.cs.carrentalrestservice.service.CarScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final CarScheduleService carScheduleService;
    private final String BASE_PATH = "api/v1/schedules";

    @GetMapping(BASE_PATH+"/{id}")
    public CarScheduleDTO getScheduleId(@PathVariable("id") Long id) {
        return carScheduleService.getCarScheduleById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(BASE_PATH)
    public ResponseEntity createSchedule(@RequestBody CarScheduleDTO carScheduleDTO) {
        carScheduleService.createCarSchedule(carScheduleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(BASE_PATH+"/{id}")
    public ResponseEntity updateSchedule(@PathVariable("id") Long id, @RequestBody CarScheduleDTO carScheduleDTO) {
        if (carScheduleService.updateCarScheduleById(carScheduleDTO, id).isEmpty())
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_PATH+"/{id}")
    public ResponseEntity deleteSchedule(@PathVariable("id") Long id) {
        if(!carScheduleService.deleteCarScheduleById(id))
            throw new NotFoundException();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
