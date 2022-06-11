package com.api.aircraftsimulationapi.model.services;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import com.api.aircraftsimulationapi.model.entities.Test;
import com.api.aircraftsimulationapi.model.entities.TestData;
import com.api.aircraftsimulationapi.model.repositories.TestDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TestDataService {
    private final TestDataRepository testDataRepository;

    public TestDataService(TestDataRepository testDataRepository) {
        this.testDataRepository = testDataRepository;
    }

    public TestData save(TestData testData) {
        return testDataRepository.save(testData);
    }

    public Set<TestData> getTestDataFromTestAndAircraft(Aircraft aircraft, int testNumber) {
        for (Test test : aircraft.getTests()) {
            if(test.getTestNumber() == testNumber)
                return test.getTestData();
        }
        return null;
    }
}
