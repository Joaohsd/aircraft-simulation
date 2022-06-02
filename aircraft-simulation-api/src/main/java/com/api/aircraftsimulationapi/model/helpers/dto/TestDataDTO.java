package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class TestDataDTO {
    @NotBlank
    private String aircraftCode;
    @NotBlank
    private String parameterCode;
    @NotNull
    private int testNumber;
    @NotNull
    private Date timeStamp;
    @NotNull
    private double value;
}
