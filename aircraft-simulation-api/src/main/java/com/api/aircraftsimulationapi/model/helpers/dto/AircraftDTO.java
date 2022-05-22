package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AircraftDTO {
    @NotBlank
    @Size(min = 5, max = 5, message = "Aircraft Code: Out of length (min:5 and max:5 characters).")
    private String aircraftCode;
    @NotBlank
    private String category;
    @NotNull
    private Boolean test;
}
