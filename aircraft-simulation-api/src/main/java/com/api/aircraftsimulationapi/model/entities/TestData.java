package com.api.aircraftsimulationapi.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class TestData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column(nullable = false)
    private Date timeStamp;
    @Column(nullable = false)
    private double value;
}
