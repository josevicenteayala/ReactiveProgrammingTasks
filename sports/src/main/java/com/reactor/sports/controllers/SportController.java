package com.reactor.sports.controllers;

import com.reactor.sports.model.Sport;
import com.reactor.sports.services.SportService;
import java.time.Duration;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping(value = "/api/v1/sports")
public class SportController {

    @Value("${sports.prefetch.rate:20}")
    public int PREFETCH_RATE;
    @Autowired
    private SportService sportService;

    @GetMapping
    public Mono<ResponseEntity<Flux<Sport>>> getAllSports() {
        return sportService.getAllSports()
                .collectList()
                .flatMap(list -> {
                    if(list.isEmpty()) {
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(list)));
                    }
                });
    }

    @GetMapping(value = "/partial")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public Flux<Sport> getAllSportsBackpressurePartial() {
        return sportService.getAllSports()
                .log()
                .subscribeOn(Schedulers.boundedElastic())
                .limitRate(PREFETCH_RATE);
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Sport> getAllSportsBackpressureStream() {
        return sportService.getAllSports()
                .log()
                .limitRate(PREFETCH_RATE)
                .flatMap(sport -> Flux.zip(
                        Flux.interval(Duration.ofSeconds(2)),
                        Flux.fromStream(Stream.generate(() -> sport))
                ).map(Tuple2::getT2));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Sport> createSport(@RequestBody Sport sport) {
        return sportService.createSport(sport);
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
                .limitRate(PREFETCH_RATE)
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
