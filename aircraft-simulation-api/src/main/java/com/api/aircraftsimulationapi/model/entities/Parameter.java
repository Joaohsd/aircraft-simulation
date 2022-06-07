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
public class Parameter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Self columns
    @Column(length = 6)
    private String code;
    @Column(length = 45)
    private String name;
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

    //Relationships
    //Relationship between Parameter and Aircraft (n:m)
    @JsonIgnore
    @ManyToMany(mappedBy = "parameters")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Set<Aircraft> aircrafts = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "parameter")
    Set<TestData> testData = new HashSet<>();

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameter parameter = (Parameter) o;
        return id == parameter.id && samplingRate == parameter.samplingRate && minValue == parameter.minValue && maxValue == parameter.maxValue && activeStatus == parameter.activeStatus && code.equals(parameter.code) && name.equals(parameter.name) && type.equals(parameter.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, type, samplingRate, minValue, maxValue, activeStatus);
    }
}
