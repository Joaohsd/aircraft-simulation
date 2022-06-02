package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.TestData;
import com.api.aircraftsimulationapi.model.repositories.TestDataRepository;
import org.springframework.stereotype.Service;

@Service
public class TestDataService {
    private final TestDataRepository testDataRepository;

    public TestDataService(TestDataRepository testDataRepository) {
        this.testDataRepository = testDataRepository;
    }

    public TestData save(TestData testData) {
        return testDataRepository.save(testData);
    }
}
