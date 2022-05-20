package com.api.aircraftsimulationapi.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
public class TestData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Relationship between TestData and Test
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name="test_number"),
            @JoinColumn(name="aircraft_code"),
    })
    private Test test;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parameter_code")
    private Parameter parameter;
    @Column(nullable = false)
    private LocalDate timeStamp;
    @Column(nullable = false)
    private double value;
}
