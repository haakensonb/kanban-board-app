package com.backend.kanbanboard.controllers;

import java.util.List;

import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.services.KanbanService;

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

    private final KanbanService kanbanService;

    CardController(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    @GetMapping("/cards")
    List<Card> all() {
        return kanbanService.getAllCards();
    }

    @GetMapping("/cards/{id}")
    Card one(@PathVariable Long id) {
        return kanbanService.getCard(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/cards")
    Card newCard(@RequestBody Card newCard) {
        return kanbanService.createCard(newCard);
    }

    @PutMapping("/cards/{id}")
    Card updateCard(@RequestBody Card newCard, @PathVariable Long id) {
        return kanbanService.updateCard(newCard, id);
    }

    @DeleteMapping("/cards/{id}")
    void deleteCard(@PathVariable Long id) {
        kanbanService.deleteCard(id);
    }
}
