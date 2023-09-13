package com.reactor.sports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.reactor.sports.repositories")
public class SportsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsApplication.class, args);
	}

}
