package com.reactor.sports.controllers;

import com.reactor.sports.model.Sport;
import com.reactor.sports.services.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/sports")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public Flux<Sport> getAllSports() {
        return sportService.getAllSports();
    }

    @PostMapping
    public Mono<Sport> createSport(@RequestBody Sport sport) {
        return sportService.createSport(sport);
    }

}
