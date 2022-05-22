package com.api.aircraftsimulationapi.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
public class TestData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Self columns
    @Column(nullable = false)
    private Date timeStamp;
    @Column(nullable = false)
    private double value;

    //Relationships
    //Relationship between TestData and Test (n:1)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="test_number", referencedColumnName = "test_number"),
            @JoinColumn(name="aircraft_code", referencedColumnName = "aircraft_code"),
    })
    private Test test;
    //Relationship between TestData and Parameter (n:1)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parameter_id", referencedColumnName = "id", nullable = false)
    private Parameter parameter;

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestData testData = (TestData) o;
        return Double.compare(testData.value, value) == 0 && id.equals(testData.id) && test.equals(testData.test) && parameter.equals(testData.parameter) && timeStamp.equals(testData.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, test, parameter, timeStamp, value);
    }
}
