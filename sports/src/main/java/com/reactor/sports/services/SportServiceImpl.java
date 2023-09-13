package com.reactor.sports.services;

import com.reactor.sports.model.Sport;
import com.reactor.sports.repositories.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
}
