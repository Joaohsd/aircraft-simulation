package com.api.aircraftsimulationapi.controller;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Engineer;
import com.api.aircraftsimulationapi.model.entities.Test;
import com.api.aircraftsimulationapi.model.helpers.dto.TestDTO;
import com.api.aircraftsimulationapi.model.helpers.test.TestEngine;
import com.api.aircraftsimulationapi.model.repositories.EngineerRepository;
import com.api.aircraftsimulationapi.model.services.AircraftService;
import com.api.aircraftsimulationapi.model.services.EngineerService;
import com.api.aircraftsimulationapi.model.services.TestService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tests")
public class TestController {
    //Services
    private final TestService testService;
    private final AircraftService aircraftService;
    private final EngineerService engineerService;

    public TestController(TestService testService, AircraftService aircraftService, EngineerService engineerService) {
        this.testService = testService;
        this.aircraftService = aircraftService;
        this.engineerService = engineerService;
    }

    //Register ONE TEST
    @PostMapping
    public ResponseEntity<Object> saveTest(@RequestBody @Valid TestDTO testDTO){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(testDTO.getAircraftCode());
        Optional<Engineer> engineerOptional = engineerService.findByCPF(testDTO.getCpfEngineer());
        if(!aircraftOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Aircraft does not exist");
        if(!engineerOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Engineer does not exist");

        var test = new Test();
        var engineer = new Engineer();
        var aircraft = new Aircraft();

        engineer = engineerOptional.get();
        aircraft = aircraftOptional.get();

        BeanUtils.copyProperties(testDTO,test);
        test.setEngineer(engineer);
        test.setAircraft(aircraft);

        // Setting test engine
        TestEngine.aircraft = test.getAircraft();
        TestEngine.time = test.getTime();
        TestEngine.testNumber = test.getTestNumber();
        // Running test engine
        TestEngine.runTest();

        return ResponseEntity.status(HttpStatus.OK).body(testService.save(test));
    }

    @GetMapping
    public ResponseEntity<Object> getAllTests(){
        return ResponseEntity.status(HttpStatus.OK).body(testService.getAllTests());
    }
}
