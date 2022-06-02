package com.api.aircraftsimulationapi.controller;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.helpers.dto.AircraftDTO;
import com.api.aircraftsimulationapi.model.helpers.dto.ParameterDTO;
import com.api.aircraftsimulationapi.model.services.AircraftService;
import com.api.aircraftsimulationapi.model.services.ParameterService;
import com.api.aircraftsimulationapi.model.services.TestService;
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
    //Services
    private final AircraftService aircraftService;
    private final ParameterService parameterService;

    public AircraftController(AircraftService aircraftService, ParameterService parameterService) {
        this.aircraftService = aircraftService;
        this.parameterService = parameterService;
    }

    //Register ONE AIRCRAFT
    @PostMapping
    public ResponseEntity<Object> saveAircraft(@RequestBody @Valid AircraftDTO aircraftDTO){
        if(aircraftService.existsByAircraftCode(aircraftDTO.getAircraftCode()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: Aircraft Code already exists");

        var aircraft = new Aircraft();

        BeanUtils.copyProperties(aircraftDTO,aircraft);

        aircraft.setAircraftCode(aircraftDTO.getAircraftCode());
        aircraft.setNumParameters(0);

        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.save(aircraft));
    }

    //Get ALL AIRCRAFTS
    @GetMapping
    public ResponseEntity<Object> getAllAircrafts(){
        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.getAllAircrafts());
    }

    //Deleting ONE AIRCRAFT
    @DeleteMapping("/{aircraftCode}")
    public ResponseEntity<Object> deleteAircraft(@PathVariable(value = "aircraftCode") String aircraftCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);

        if(!aircraftOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Aircraft not found");

        aircraftService.delete(aircraftOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Aircraft deleted");
    }

    //Get ONE AIRCRAFT
    @GetMapping("/{aircraftCode}")
    public ResponseEntity<Object> getOneAircraft(@PathVariable(value = "aircraftCode") String aircraftCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);
        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(aircraftOptional.get());
    }

    //Get ALL PARAMETERS from ONE AIRCRAFT
    @GetMapping("/{aircraftCode}/parameters")
    public ResponseEntity<Object> getAllParametersFromOneAircraft(@PathVariable(value = "aircraftCode") String aircraftCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);
        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(aircraftOptional.get().getParameters());
    }

    //Get ONE PARAMETER from ONE AIRCRAFT
    @GetMapping("/{aircraftCode}/parameters/{parameterCode}")
    public ResponseEntity<Object> getOneParameterFromAircraft(@PathVariable(value = "aircraftCode") String aircraftCode,
                                                    @PathVariable(value= "parameterCode") String parameterCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);

        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        if(!parameterService.existsByParameterCodeAndAircraft(parameterCode,aircraftOptional.get())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parameter not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(aircraftService.findByParameterCodeAndAircraft(parameterCode,aircraftOptional.get()));
    }

    @DeleteMapping("/{aircraftCode}/parameters/{parameterCode}")
    public ResponseEntity<Object> deleteOneParameterFromAircraft(@PathVariable(value = "aircraftCode") String aircraftCode,
                                                                @PathVariable(value= "parameterCode") String parameterCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);

        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        if(!parameterService.existsByParameterCodeAndAircraft(parameterCode,aircraftOptional.get())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parameter not found");
        }

        Parameter parameter = aircraftService.findByParameterCodeAndAircraft(parameterCode,aircraftOptional.get());
        parameterService.delete(parameter);
        return ResponseEntity.status(HttpStatus.OK).body("Parameter Deleted");
    }

    @PutMapping("/{aircraftCode}/parameters/{parameterCode}")
    public ResponseEntity<Object> updateOneParameterFromAircraft(@PathVariable(value = "aircraftCode") String aircraftCode,
                                                                 @PathVariable(value= "parameterCode") String parameterCode,
                                                                 @RequestBody @Valid ParameterDTO parameterDTO){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);

        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        if(!parameterService.existsByParameterCodeAndAircraft(parameterCode,aircraftOptional.get())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parameter not found");
        }

        Parameter parameterOptional = aircraftService.findByParameterCodeAndAircraft(parameterCode,aircraftOptional.get());

        var parameter = new Parameter();

        BeanUtils.copyProperties(parameterDTO,parameter);
        parameter.setId(parameterOptional.getId());

        return ResponseEntity.status(HttpStatus.OK).body(parameterService.update(parameter,aircraftOptional.get()));
    }

    @GetMapping("/{aircraftCode}/tests")
    public ResponseEntity<Object> getAllTestsFromAircraft(@PathVariable(value = "aircraftCode") String aircraftCode){
        Optional<Aircraft> aircraftOptional = aircraftService.findByAircraftCode(aircraftCode);
        if(!aircraftOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aircraft not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(aircraftOptional.get().getTests());
    }
}
