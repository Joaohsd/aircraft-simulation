package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TestDTO {
    @NotBlank
    private int testNumber;
    @NotBlank
    private String testName;
}
