package com.backend.kanbanboard.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.kanbanboard.assemblers.CardModelAssembler;
import com.backend.kanbanboard.assemblers.ListModelAssembler;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
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
public class ListController {
    private final KanbanService kanbanService;

    private final ListModelAssembler assembler;

    private final CardModelAssembler cardAssembler;

    public ListController(KanbanService kanbanService, ListModelAssembler assembler, CardModelAssembler cardAssembler) {
        this.kanbanService = kanbanService;
        this.assembler = assembler;
        this.cardAssembler = cardAssembler;
    }

    @GetMapping("/lists")
    public CollectionModel<EntityModel<ListModel>> all() {
        List<EntityModel<ListModel>> lists = kanbanService.getAllLists().stream().map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lists, linkTo(methodOn(ListController.class).all()).withSelfRel());
    }

    @GetMapping("/lists/{id}")
    public EntityModel<ListModel> one(@PathVariable Long id) {
        ListModel list = kanbanService.getList(id);
        return assembler.toModel(list);
    }

    @GetMapping("/lists/{id}/cards")
    public CollectionModel<EntityModel<Card>> oneWithCards(@PathVariable Long id) {
        List<EntityModel<Card>> cards = kanbanService.getListCards(id).stream().map(cardAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(cards, linkTo(methodOn(ListController.class).oneWithCards(id)).withSelfRel());
    }

    @PostMapping("/lists")
    public ResponseEntity<?> newList(@RequestBody ListModel newList) {
        EntityModel<ListModel> entityModel = assembler.toModel(kanbanService.createList(newList));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/lists/{id}")
    public ResponseEntity<?> updateList(@RequestBody ListModel newList, @PathVariable Long id) {
        EntityModel<ListModel> entityModel = assembler.toModel(kanbanService.updateList(newList, id));

        // "created" may not be the best response code.
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/lists/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id) {
        kanbanService.deleteList(id);

        return ResponseEntity.noContent().build();
    }

}
