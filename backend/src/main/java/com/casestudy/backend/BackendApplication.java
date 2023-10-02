package com.casestudy.backend;

import com.casestudy.backend.security.DatabasePopulator;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	private final DatabasePopulator databasePopulator;

	public BackendApplication(DatabasePopulator databasePopulator) {
		this.databasePopulator = databasePopulator;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@PostConstruct
	public void populateDatabase() {
		databasePopulator.populateDatabase();
	}
}
