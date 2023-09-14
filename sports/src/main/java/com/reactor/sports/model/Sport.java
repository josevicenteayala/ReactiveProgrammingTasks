package com.reactor.sports.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@EqualsAndHashCode(of = {"id","name"})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table("sports")
@Entity
public class Sport implements Persistable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

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
