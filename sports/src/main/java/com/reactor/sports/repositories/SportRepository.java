package com.reactor.sports.repositories;

import com.reactor.sports.model.Sport;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends ReactiveCrudRepository<Sport, Integer> {
}
