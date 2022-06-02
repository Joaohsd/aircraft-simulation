package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.repositories.ParameterRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.ListView;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Service
public class ParameterService {
    private final ParameterRepository parameterRepository;
    private final AircraftService aircraftService;

    public ParameterService(ParameterRepository parameterRepository, AircraftService aircraftService) {
        this.parameterRepository = parameterRepository;
        this.aircraftService = aircraftService;
    }

    public boolean existsByParameterCodeAndAircraft(String code, Aircraft aircraft) {
        return parameterRepository.existsByCodeAndAircrafts(code,aircraft);
    }

    @Transactional
    public Parameter save(Parameter parameter, Aircraft aircraft) {
        Parameter auxParameter = parameterRepository.save(parameter);
        aircraftService.registerParameterInAircraft(auxParameter,aircraft);
        return auxParameter;
    }

    public List<Parameter> getAllParameters() {
        return parameterRepository.findAll();
    }

    public Object update(Parameter parameter, Aircraft aircraft) {
        Parameter auxParameter = parameterRepository.save(parameter);
        aircraftService.updateParameterInAircraft(auxParameter,aircraft);
        return auxParameter;
    }

    public void delete(Parameter parameter) {
        parameterRepository.delete(parameter);
    }
}
