package com.backend.kanbanboard;

import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.repositories.CardRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(CardRepository repo) {
        return args -> {
            log.info("Preloading " + repo.save(new Card("Test Card 1", "blash blah")));
            log.info("Preloading " + repo.save(new Card("Test Card 2", "blah blah blah")));
        };
    }
}
