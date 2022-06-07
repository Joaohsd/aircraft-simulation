package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class TestDTO {
    @NotNull
    private int testNumber;
    @NotBlank
    private String testName;
    @NotBlank
    @Size(min = 5, max = 5, message = "Aircraft Code: Out of length (min:5 and max:5 characters).")
    private String aircraftCode;
    @NotBlank
    @Size(min = 11, max = 11, message = "CPF: Out of length (min:11 and max:11 characters).")
    private String cpfEngineer;
    @NotNull
    private int time;
    @NotNull
    private Date testDate;
}
