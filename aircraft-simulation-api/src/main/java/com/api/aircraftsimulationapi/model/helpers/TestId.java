package com.api.aircraftsimulationapi.model.helpers;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestId implements Serializable {
    private int testNumber;
    private String aircraftCode;

    public TestId(int testNumber, String aircraftCode) {
        this.testNumber = testNumber;
        this.aircraftCode = aircraftCode;
    }
}
