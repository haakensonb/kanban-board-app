package com.backend.kanbanboard.controllers;

import java.util.List;

import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
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
public class ListController {
    private final KanbanService kanbanService;

    ListController(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    @GetMapping("/lists")
    List<ListModel> all() {
        return kanbanService.getAllLists();
    }

    @GetMapping("/lists/{id}")
    ListModel one(@PathVariable Long id) {
        return kanbanService.getList(id);
    }

    @GetMapping("/lists/{id}/cards")
    List<Card> oneWithCards(@PathVariable Long id) {
        return kanbanService.getListCards(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/lists")
    ListModel newList(@RequestBody ListModel newList) {
        return kanbanService.createList(newList);
    }

    @PutMapping("/lists/{id}")
    ListModel updateList(@RequestBody ListModel newList, @PathVariable Long id) {
        return kanbanService.updateList(newList, id);
    }

    @DeleteMapping("/lists/{id}")
    void deleteCard(@PathVariable Long id) {
        kanbanService.deleteList(id);
    }

}
