package com.reactor.sports.services;

import com.reactor.sports.model.Sport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SportService {

    Flux<Sport> getAllSports();

    Mono<Sport> createSport(Sport sport);

    Mono<Sport> createSport(String sport);

    Flux<Sport> getSportsByName(String sport);
}
