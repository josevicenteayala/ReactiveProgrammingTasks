package com.reactor.sports.controllers;

import com.reactor.sports.model.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SportControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testPostEndpoint() {
        Sport sportEntity = createSportEntityWithoutId("Hockey");
        webTestClient
                .post()
                .uri("/api/v1/sports")
                .body(Mono.just(sportEntity), Sport.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Sport.class)
                .value(response -> response.equals(sportEntity));
    }

    @Test
    public void testPostByNameEndpoint() {
        Sport sportEntity = new Sport("Baseball");
        webTestClient
                .post()
                .uri("/api/v1/sports/Baseball")
                .body(Mono.just(sportEntity), Sport.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Sport.class)
                .value(response -> response.equals(sportEntity));
    }

    @Test
    public void testGetAllSportsEndpoint() {
        webTestClient
                .get()
                .uri("/api/v1/sports")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("""
                        [{"id":1,"name":"Hockey","new":false}]""");
    }

    @Test
    public void testGetByNameEndpoint() {
        webTestClient
                .get()
                .uri("/api/v1/sports/byName?sportName=Hockey")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("""
                        [{"id":1,"name":"Hockey","new":false}]""");
    }

    @Test
    public void testGetByNameNotFoundSport() {
        webTestClient
                .get()
                .uri("/api/v1/sports/byName?sportName=NoSport")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .isEqualTo(null);
    }

    @Test
    public void testGetAllSportsUsingPartialContent() {
        Sport sportEntity = createSportEntityWithoutId("Aerobics");
        for (int i = 0;i<40;i++) {
            webTestClient
                    .post()
                    .uri("/api/v1/sports")
                    .body(Mono.just(sportEntity), Sport.class)
                    .exchange();
        }

        webTestClient
                .get()
                .uri("/api/v1/sports/partial")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.PARTIAL_CONTENT)
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(String.class);
    }

    private Sport createSportEntityWithoutId(String sport) {
        return new Sport(sport);
    }
}