package com.api.aircraftsimulationapi.controller;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Engineer;
import com.api.aircraftsimulationapi.model.helpers.dto.EngineerDTO;
import com.api.aircraftsimulationapi.model.services.EngineerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/engineers")
public class EngineerController {
    //Services
    final EngineerService engineerService;

    public EngineerController(EngineerService engineerService) {
        this.engineerService = engineerService;
    }

    //Register ONE ENGINEER
    @PostMapping
    public ResponseEntity<Object> saveEngineer(@RequestBody @Valid EngineerDTO engineerDTO){
        if(engineerService.existsByCPF(engineerDTO.getCpf()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERROR: CPF already exists");

        var engineer = new Engineer();

        BeanUtils.copyProperties(engineerDTO, engineer);
        engineer.setCpf(engineerDTO.getCpf());

        return ResponseEntity.status(HttpStatus.OK).body(engineerService.save(engineer));
    }

    //Get ALL ENGINEERS
    @GetMapping
    public ResponseEntity<Object> getAllEngineers(){
        return ResponseEntity.status(HttpStatus.OK).body(engineerService.getAllEnginneers());
    }

    @DeleteMapping("/{cpfEngineer}")
    public ResponseEntity<Object> deleteEngineer(@PathVariable(value = "cpfEngineer") String cpfEngineer){
        Optional<Engineer> engineerOptional = engineerService.findByCPF(cpfEngineer);

        if(!engineerOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERROR: Engineer not found");

        engineerService.delete(engineerOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body("Aircraft deleted");
    }

    //Get ONE ENGINEER
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEngineer(@PathVariable(value = "id") String cpf){
        Optional<Engineer> engineerOptional = engineerService.findByCPF(cpf);

        if(!engineerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Engineer not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(engineerOptional.get());
    }
}
