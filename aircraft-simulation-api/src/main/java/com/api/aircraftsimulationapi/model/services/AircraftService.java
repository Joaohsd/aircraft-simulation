package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Parameter;
import com.api.aircraftsimulationapi.model.repositories.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Aircraft> findById(String aircraftCode) {
        return aircraftRepository.findById(aircraftCode);
    }

    public void registerParameterInAircraft(Parameter parameter, Aircraft aircraft) {
        aircraft.addParameter(parameter);
    }
}