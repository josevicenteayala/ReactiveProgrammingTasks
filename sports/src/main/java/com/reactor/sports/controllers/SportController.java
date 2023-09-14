package com.reactor.sports.controllers;

import com.reactor.sports.model.Sport;
import com.reactor.sports.services.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping(value = "/api/v1/sports")
public class SportController {

    @Autowired
    private SportService sportService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Sport>>> getAllSports() {
        return sportService.getAllSports()
                .collectList()
                .flatMap(list ->{
                    if(list.isEmpty()){
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(list)));
                    }
                });
    }

    @PostMapping
    public Mono<ResponseEntity<Sport>> createSport(@RequestBody Sport sport) {
        return sportService.createSport(sport)
                .map(createdSport -> ResponseEntity.status(HttpStatus.CREATED).body(createdSport));
    }

    @PostMapping("/{sportName}")
    public Mono<ResponseEntity<Sport>> createSportByName(@PathVariable(name = "sportName") String sportName) {
        return sportService.createSport(sportName)
                .map(createdSport -> ResponseEntity.status(HttpStatus.CREATED).body(createdSport));
    }

    @GetMapping("/byName")
    public Mono<ResponseEntity<Flux<Sport>>> getSportsByName(@RequestParam(name = "sportName") String sportName) {
        return sportService.getSportsByName(sportName)
                .collectList()
                .flatMap(list -> {
                    if(list.isEmpty()) {
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(list)));
                    }
                });
    }

    @GetMapping("/byNamePartialContent")
    public Mono<ResponseEntity<Flux<Sport>>> getSportsByNamePartialContent(@RequestParam(name = "sportName") String sportName) {
        return sportService.getSportsByName(sportName)
                .limitRate(2)
                .log()
                .subscribeOn(Schedulers.boundedElastic())
                .collectList()
                .flatMap(list -> {
                    if(list.isEmpty()) {
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(list)));
                    }
                });
    }


}
