package com.api.aircraftsimulationapi.controller;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.helpers.dto.ParameterDTO;
import com.api.aircraftsimulationapi.model.services.AircraftService;
import com.api.aircraftsimulationapi.model.services.ParameterService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/parameters")
public class ParameterController {
    private final ParameterService parameterService;
    private final AircraftService aircraftService;

    public ParameterController(ParameterService parameterService, AircraftService aircraftService) {
        this.parameterService = parameterService;
        this.aircraftService = aircraftService;
    }

    //Register ONE PARAMETER
    @PostMapping
    public ResponseEntity<Object> saveParameter(@RequestBody @Valid ParameterDTO parameterDTO) {
        Optional<Aircraft> aircraft = aircraftService.findById(parameterDTO.getAircraftCode());
        if(!aircraft.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Aircraft does not exist");
        if(parameterService.existsByCodeAndAircrafts(parameterDTO.getCode(),aircraft.get()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: Parameter " + parameterDTO.getCode() + " already exists at aircraft " + parameterDTO.getAircraftCode());
        var parameter = new Parameter();
        BeanUtils.copyProperties(parameterDTO,parameter);
        return ResponseEntity.status(HttpStatus.CREATED).body(parameterService.save(parameter,aircraft.get()));
    }
}
