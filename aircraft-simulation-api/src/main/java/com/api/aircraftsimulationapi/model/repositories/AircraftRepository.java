package com.api.aircraftsimulationapi.model.repositories;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AircraftRepository extends JpaRepository<Aircraft, String> {
}
