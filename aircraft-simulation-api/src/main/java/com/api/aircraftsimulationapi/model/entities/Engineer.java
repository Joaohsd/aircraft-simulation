package com.api.aircraftsimulationapi.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Engineer implements Serializable {
    @Id
    private String cpf;
    @Column(nullable = false, unique = true)
    private String registration;
    @Column(nullable = false)
    private String nome;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    @OneToMany(mappedBy = "engineer")
    private Set<Test> tests = new HashSet<>();
}
