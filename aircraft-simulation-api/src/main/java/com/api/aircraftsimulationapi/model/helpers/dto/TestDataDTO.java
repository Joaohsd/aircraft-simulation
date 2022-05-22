package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TestDataDTO {
    @NotBlank
    private Date timeStamp;
    @NotBlank
    private double value;
}
