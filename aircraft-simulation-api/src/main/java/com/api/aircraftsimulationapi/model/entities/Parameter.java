package com.api.aircraftsimulationapi.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Parameter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 6)
    private String code;
    @Column(nullable = false)
    private String type;
    @Column(name = "sampling_rate", nullable = false)
    private int samplingRate;
    @Column(name = "min_value", nullable = false)
    private int minValue;
    @Column(name = "max_value", nullable = false)
    private int maxValue;
    @Column(name = "active_status", nullable = false)
    private boolean activeStatus;
    @ManyToMany(mappedBy = "parameters")
    private Set<Aircraft> aircrafts = new HashSet<>();
    @OneToMany(mappedBy = "parameter")
    Set<TestData> testData = new HashSet<>();
}
