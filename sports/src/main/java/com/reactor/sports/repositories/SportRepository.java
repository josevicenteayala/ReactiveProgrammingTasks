package com.reactor.sports.repositories;

import com.reactor.sports.model.Sport;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SportRepository extends ReactiveCrudRepository<Sport, Integer> {

    Flux<Sport> findByName(String sport);

}
