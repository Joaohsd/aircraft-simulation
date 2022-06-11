package com.api.aircraftsimulationapi.controller;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.entities.Test;
import com.api.aircraftsimulationapi.model.entities.TestData;
import com.api.aircraftsimulationapi.model.helpers.dto.TestDTO;
import com.api.aircraftsimulationapi.model.helpers.dto.TestDataDTO;
import com.api.aircraftsimulationapi.model.services.AircraftService;
import com.api.aircraftsimulationapi.model.services.ParameterService;
import com.api.aircraftsimulationapi.model.services.TestDataService;
import com.api.aircraftsimulationapi.model.services.TestService;
import org.apache.coyote.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/test-data")
public class TestDataController {
    private final TestDataService testDataService;
    private final AircraftService aircraftService;
    private final ParameterService parameterService;
    private final TestService testService;


    public TestDataController(TestDataService testDataService, AircraftService aircraftService, ParameterService parameterService, TestService testService) {
        this.testDataService = testDataService;
        this.aircraftService = aircraftService;
        this.parameterService = parameterService;
        this.testService = testService;
    }

    @PostMapping
    ResponseEntity<Object> saveTestData(@RequestBody @Valid TestDataDTO testDataDTO){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(testDataDTO.getAircraftCode());
        if(!aircraftOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Aircraft not found");
        if(!parameterService.existsByParameterCodeAndAircraft(testDataDTO.getParameterCode(),aircraftOptional.get()))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Parameter not found");

        Parameter parameter = aircraftService.findByParameterCodeAndAircraft(testDataDTO.getParameterCode(),aircraftOptional.get());
        Test test = aircraftService.findByTestNumberAndAircraft(testDataDTO.getTestNumber(), aircraftOptional.get());

        var testData = new TestData();
        BeanUtils.copyProperties(testDataDTO,testData);
        testData.setParameter(parameter);
        testData.setTest(test);
        return ResponseEntity.status(HttpStatus.OK).body(testDataService.save(testData));
    }

}
