package com.api.aircraftsimulationapi.model.repositories;

import com.api.aircraftsimulationapi.model.entities.TestData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDataRepository extends JpaRepository<TestData, Integer> {
}
