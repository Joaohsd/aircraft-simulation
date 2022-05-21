package com.api.aircraftsimulationapi.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Aircraft implements Serializable {
    @Id
    @Column(name = "aircraft_code")
    private String aicraftCode;
    @Column(nullable = false)
    private String category;
    @Column(name = "num_parameters",nullable = false)
    private int numParameters;
    @Column(nullable = false)
    private boolean test;
    //Relationship between Aircraft and Parameter (n:m)
    @ManyToMany
    @JoinTable(
            name = "AircraftParameter",
            joinColumns = @JoinColumn(name = "aircraft_code"),
            inverseJoinColumns = @JoinColumn(name = "parameter_id")
    )
    private Set<Parameter> parameters =  new HashSet<>();
    //Relationship between Aircraft and Test (1:n)
    @OneToMany(mappedBy = "aircraft")
    private Set<Test> tests = new HashSet<>();
}
