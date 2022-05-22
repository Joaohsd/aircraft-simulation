package com.api.aircraftsimulationapi.model.helpers.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class EngineerDTO {
    @NotBlank
    @Size(min = 11, max = 11, message = "CPF: Out of length (min:11 and max:11 characters).")
    private String cpf;
    @NotBlank
    private String registration;
    @NotBlank
    private String name;
    @NotNull
    private Date birthDate;
}
