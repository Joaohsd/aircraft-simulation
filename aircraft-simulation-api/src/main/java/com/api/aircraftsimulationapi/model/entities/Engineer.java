package com.api.aircraftsimulationapi.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
public class Engineer implements Serializable {
    @Id
    private String cpf;

    //Self columns
    @Column(nullable = false, unique = true)
    private String registration;
    @Column(nullable = false)
    private String name;
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    //Relationships
    //Relationship between Engineer and Test (1:n)
    @JsonIgnore
    @OneToMany(mappedBy = "engineer")
    private Set<Test> tests = new HashSet<>();

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engineer engineer = (Engineer) o;
        return cpf.equals(engineer.cpf) && registration.equals(engineer.registration) && name.equals(engineer.name) && birthDate.equals(engineer.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, registration, name, birthDate);
    }
}
