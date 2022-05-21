package com.api.aircraftsimulationapi.model.helpers;

import com.api.aircraftsimulationapi.model.entities.Aircraft;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class TestId implements Serializable {
    private int testNumber;
    private Aircraft aircraft;
}
