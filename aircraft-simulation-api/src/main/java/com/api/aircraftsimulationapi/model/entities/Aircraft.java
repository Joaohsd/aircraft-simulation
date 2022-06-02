package com.api.aircraftsimulationapi.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    private String aircraftCode;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "aircraft_parameter",
            joinColumns = {@JoinColumn(name = "aircraft_code", referencedColumnName = "aircraft_code")},
            inverseJoinColumns = {@JoinColumn(name = "parameter_id", referencedColumnName = "id")}
    )
    private Set<Parameter> parameters =  new HashSet<>();

    //Relationship between Aircraft and Test (1:n)
    @JsonIgnore
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
        return numParameters == aircraft.numParameters && test == aircraft.test && Objects.equals(aircraftCode, aircraft.aircraftCode) && Objects.equals(category, aircraft.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aircraftCode, category, numParameters, test);
    }
}
