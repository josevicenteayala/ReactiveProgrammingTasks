package com.reactor.sports.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("sports")
@Entity
public class Sport implements Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    public Sport() {}

    public Sport(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sport(String name) {
        this.name = name;
    }
    @Override
    public Object getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
