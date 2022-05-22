package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ParameterDTO {
    @NotBlank
    @Size(min = 6, max = 6, message = "Parameter Code: Out of length (min:6 and max:6 characters).")
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotNull
    private int samplingRate;
    @NotNull
    private int minValue;
    @NotNull
    private int maxValue;
    @NotNull
    private Boolean activeStatus;
    @NotBlank
    private String aircraftCode;
}
