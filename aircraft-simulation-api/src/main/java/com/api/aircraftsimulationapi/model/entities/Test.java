package com.api.aircraftsimulationapi.model.entities;

import com.api.aircraftsimulationapi.model.helpers.TestId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
//@IdClass(TestId.class)
public class Test implements Serializable {
    @Id
    @Column(name = "test_number")
    private int testNumber;
    @Column(name = "test_name", nullable = false)
    private String testName;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf_engineer", referencedColumnName = "cpf", nullable = false)
    private Engineer engineer;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aircraft_code", referencedColumnName = "aircraft_code", nullable = false)
    private Aircraft aircraft;
    @OneToMany(mappedBy = "test")
    Set<TestData> testData = new HashSet<>();
}
