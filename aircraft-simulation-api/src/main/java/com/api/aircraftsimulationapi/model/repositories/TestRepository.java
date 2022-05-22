package com.api.aircraftsimulationapi.model.repositories;

import com.api.aircraftsimulationapi.model.entities.Test;
import com.api.aircraftsimulationapi.model.helpers.keys.TestId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, TestId> {
}
