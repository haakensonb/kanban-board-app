package com.backend.kanbanboard.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.kanbanboard.assemblers.CardModelAssembler;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.services.KanbanService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private final KanbanService kanbanService;

    private final CardModelAssembler assembler;

    public CardController(KanbanService kanbanService, CardModelAssembler assembler) {
        this.kanbanService = kanbanService;
        this.assembler = assembler;
    }

    @GetMapping("/cards")
    public CollectionModel<EntityModel<Card>> all() {
        List<EntityModel<Card>> cards = kanbanService.getAllCards().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cards, linkTo(methodOn(CardController.class).all()).withSelfRel());
    }

    @GetMapping("/cards/{id}")
    public EntityModel<Card> one(@PathVariable Long id) {
        Card card = kanbanService.getCard(id);
        return assembler.toModel(card);
    }

    @PostMapping("/cards")
    public ResponseEntity<?> newCard(@RequestBody Card newCard) {
        EntityModel<Card> entityModel = assembler.toModel(kanbanService.createCard(newCard));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<?> updateCard(@RequestBody Card newCard, @PathVariable Long id) {
        EntityModel<Card> entityModel = assembler.toModel(kanbanService.updateCard(newCard, id));

        // "created" may not be the best response code.
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        kanbanService.deleteCard(id);

        return ResponseEntity.noContent().build();
    }
}
