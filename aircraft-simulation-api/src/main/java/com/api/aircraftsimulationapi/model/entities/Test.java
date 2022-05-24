package com.api.aircraftsimulationapi.model.entities;

import com.api.aircraftsimulationapi.model.helpers.keys.TestId;
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
@IdClass(TestId.class)
public class Test implements Serializable {
    //Composite Key
    @Id
    @Column(name = "test_number", nullable = false)
    private int testNumber;

    //Relationship between Test and Aircraft (n:1)
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_code", referencedColumnName = "aircraft_code", nullable = false)
    private Aircraft aircraft;

    //Self columns
    @Column(name = "test_name", nullable = false)
    private String testName;

    //Relationships
    //Relationship between Test and Engineer (n:1)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf_engineer", referencedColumnName = "cpf", nullable = false)
    private Engineer engineer;
    //Relationship between Parameter and TestData (1:n)
    @OneToMany(mappedBy = "test")
    Set<TestData> testData = new HashSet<>();

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return testNumber == test.testNumber && aircraft.equals(test.aircraft) && testName.equals(test.testName) && engineer.equals(test.engineer) && Objects.equals(testData, test.testData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testNumber, aircraft, testName, engineer, testData);
    }
}
