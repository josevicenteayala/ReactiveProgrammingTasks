package com.reactor.sports.services;

import com.reactor.sports.model.Sport;
import com.reactor.sports.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class SportServiceImpl implements SportService {

    private final SportRepository sportRepository;

    @Autowired
    public SportServiceImpl(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    @Override
    public Flux<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    @Override
    public Mono<Sport> createSport(Sport sport) {
        return sportRepository.save(sport);
    }

    @Override
    public Mono<Sport> createSport(String sport) {
        return sportRepository.save(new Sport(sport));
    }

    @Override
    public Flux<Sport> getSportsByName(String sport) {
        return sportRepository.findByName(sport);
    }
}
