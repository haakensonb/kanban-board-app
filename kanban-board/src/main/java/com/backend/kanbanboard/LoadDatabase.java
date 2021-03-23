package com.backend.kanbanboard;

import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.repositories.CardRepository;
import com.backend.kanbanboard.repositories.ListRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    // Need profile to stop method from running during test with active "test" profile.
    @Profile("!test")
    @Bean
    CommandLineRunner initDatabase(CardRepository cardRepo, ListRepository listRepo) {
        return args -> {
            ListModel list1 = listRepo.save(new ListModel("Test List 1"));
            log.info("Preloading " + list1);
            Card card1 = new Card("Test Card 1", "blash blah", list1);
            Card card2 = new Card("Test Card 2", "blah blah blah", list1);
            log.info("Preloading " + cardRepo.save(card1));
            log.info("Preloading " + cardRepo.save(card2));
        };
    }
}
