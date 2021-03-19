package com.backend.kanbanboard.controllers;

import java.util.List;

import com.backend.kanbanboard.exceptions.CardNotFoundException;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.repositories.CardRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
    private final CardRepository repo;

    CardController(CardRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/cards")
    List<Card> all() {
        return repo.findAll();
    }

    @GetMapping("/cards/{id}")
    Card one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new CardNotFoundException(id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/cards")
    Card newCard(@RequestBody Card newCard) {
        // If no id is given, then save and autogenerate an id.
        if(newCard.getId() == null){
            return repo.save(newCard);
        } else {
            // If id already exists, return it
            // otherwise create object with the specified id.
            return repo.findById(newCard.getId()).orElseGet(() -> {
                return repo.save(newCard);
            });
        }
        
    }

    @PutMapping("/cards/{id}")
    Card updateCard(@RequestBody Card newCard, @PathVariable Long id) {
        return repo.findById(id).map(card -> {
            card.setTitle(newCard.getTitle());
            card.setDescription(newCard.getDescription());
            return repo.save(card);
        }).orElseGet(() -> {
            newCard.setId(id);
            return repo.save(newCard);
        });
    }

    @DeleteMapping("/cards/{id}")
    void deleteCard(@PathVariable Long id) {
        repo.deleteById(id);
    }
}