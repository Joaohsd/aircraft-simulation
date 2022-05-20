package com.api.aircraftsimulationapi.model.repositories;

import com.api.aircraftsimulationapi.model.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Parameter, Integer> {
}
