package com.api.aircraftsimulationapi.controller;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.helpers.dto.AircraftDTO;
import com.api.aircraftsimulationapi.model.services.AircraftService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/aircrafts")
public class AircraftController {
    final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    //Register ONE AIRCRAFT
    @PostMapping
    public ResponseEntity<Object> saveAircraft(@RequestBody @Valid AircraftDTO aircraftDTO){
        if(aircraftService.existsByAircraftCode(aircraftDTO.getAircraftCode()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: Aircraft Code already exists");
        var aircraft = new Aircraft();
        BeanUtils.copyProperties(aircraftDTO,aircraft);
        aircraft.setAicraftCode(aircraftDTO.getAircraftCode());
        aircraft.setNumParameters(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraftService.save(aircraft));
    }

    //Get ALL AIRCRAFTS
    @GetMapping
    public ResponseEntity<Object> getAllAircrafts(){
        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.getAllAircrafts());
    }

    //Get ONE AIRCRAFT
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") String aircraftCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findById(aircraftCode);
        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(aircraftOptional.get());
    }
}
