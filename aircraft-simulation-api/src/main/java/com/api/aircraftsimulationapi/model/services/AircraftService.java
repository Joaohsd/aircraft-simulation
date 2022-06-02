package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.entities.Test;
import com.api.aircraftsimulationapi.model.repositories.AircraftRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AircraftService {
    private final AircraftRepository aircraftRepository;

    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Transactional
    public Aircraft save(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public boolean existsByAircraftCode(String aicraftCode) {
        return aircraftRepository.existsById(aicraftCode);
    }

    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    public Optional<Aircraft> findByAircraftCode(String aircraftCode) {
        return aircraftRepository.findById(aircraftCode);
    }

    public void registerParameterInAircraft(Parameter parameter, Aircraft aircraft) {
        aircraft.addParameter(parameter);
    }

    //Return the parameter from an aircraft based on parameter code passed to this service
    public Parameter findByParameterCodeAndAircraft(String parameterCode, Aircraft aircraft) {
        for (Parameter p : aircraft.getParameters()) {
            if(p.getCode().equals(parameterCode))
                return p;
        }
        return null;
    }

    @Transactional
    public void delete(Aircraft aircraft) {
        aircraftRepository.delete(aircraft);
    }

    @Transactional
    public void deleteParameterFromAircraft(String parameterCode, Aircraft aircraft) {
        Parameter parameter = null;
        for (Parameter p : aircraft.getParameters()) {
            if(p.getCode().equals(parameterCode)) {
                parameter = p;
            }
        }
        aircraft.getParameters().remove(parameter);
    }

    public Parameter updateParameterInAircraft(Parameter parameter, Aircraft aircraft) {
        Set<Parameter> parameters = aircraft.getParameters();
        Parameter auxParameter = null;
        for (Parameter p : parameters) {
            if(p.getCode().equals(parameter.getCode())){
                auxParameter = p;
                break;
            }
        }
        BeanUtils.copyProperties(parameter,auxParameter);
        return auxParameter;
    }

    public Test findByTestNumberAndAircraft(int testNumber, Aircraft aircraft) {
        for (Test test : aircraft.getTests()) {
            if(test.getTestNumber() == testNumber)
                return test;
        }
        return null;
    }
}
