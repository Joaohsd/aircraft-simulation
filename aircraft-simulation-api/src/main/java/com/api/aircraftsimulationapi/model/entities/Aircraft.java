package com.api.aircraftsimulationapi.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Aircraft implements Serializable {
    @Id
    @Column(name = "aircraft_code")
    private String aicraftCode;

    //Self columns
    @Column(nullable = false)
    private String category;
    @Column(name = "num_parameters",nullable = false)
    private int numParameters;
    @Column(nullable = false)
    private boolean test;

    //Relationships
    //Relationship between Aircraft and Parameter (n:m)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "aircraft_parameter",
            joinColumns = {@JoinColumn(name = "aircraft_code", referencedColumnName = "aircraft_code")},
            inverseJoinColumns = {@JoinColumn(name = "parameter_id", referencedColumnName = "id")}
    )
    private Set<Parameter> parameters =  new HashSet<>();

    //Relationship between Aircraft and Test (1:n)
    @OneToMany(mappedBy = "aircraft")
    private Set<Test> tests = new HashSet<>();

    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
        this.numParameters++;
    }

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aircraft aircraft = (Aircraft) o;
        return numParameters == aircraft.numParameters && test == aircraft.test && Objects.equals(aicraftCode, aircraft.aicraftCode) && Objects.equals(category, aircraft.category) && Objects.equals(tests, aircraft.tests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aicraftCode, category, numParameters, test, tests);
    }
}
