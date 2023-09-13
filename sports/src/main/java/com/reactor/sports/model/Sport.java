package com.reactor.sports.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("sports")
public class Sport {

    @Id
    private Integer id;
    private String name;
}
