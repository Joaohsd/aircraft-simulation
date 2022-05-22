package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.repositories.ParameterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

@Service
public class ParameterService {
    private final ParameterRepository parameterRepository;
    private final AircraftService aircraftService;

    public ParameterService(ParameterRepository parameterRepository, AircraftService aircraftService) {
        this.parameterRepository = parameterRepository;
        this.aircraftService = aircraftService;
    }

    public boolean existsByCodeAndAircrafts(String code, Aircraft aircraft) {
        return parameterRepository.existsByCodeAndAircrafts(code,aircraft);
    }

    @Transactional
    public Parameter save(Parameter parameter, Aircraft aircraft) {
        Parameter auxParameter = parameterRepository.save(parameter);
        aircraftService.registerParameterInAircraft(auxParameter,aircraft);
        return auxParameter;
    }
}
