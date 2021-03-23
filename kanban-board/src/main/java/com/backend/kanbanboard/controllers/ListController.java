package com.backend.kanbanboard.controllers;

import java.util.List;

import com.backend.kanbanboard.exceptions.ListNotFoundException;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.repositories.CardRepository;
import com.backend.kanbanboard.repositories.ListRepository;

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
    private final ListRepository listRepo;

    private final CardRepository cardRepo;

    ListController(ListRepository listRepo, CardRepository cardRepo){
        this.listRepo = listRepo;
        this.cardRepo = cardRepo;
    }

    @GetMapping("/lists")
    List<ListModel> all(){
        return listRepo.findAll();
    }

    @GetMapping("/lists/{id}")
    ListModel one(@PathVariable Long id){
        return listRepo.findById(id)
                .orElseThrow(() -> new ListNotFoundException(id));
    }

    @GetMapping("/lists/{id}/cards")
    List<Card> oneWithCards(@PathVariable Long id){
        ListModel list = listRepo.findById(id)
                .orElseThrow(() -> new ListNotFoundException(id));
        return cardRepo.findByListId(list.getId());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/lists")
    ListModel newList(@RequestBody ListModel newList){
        return listRepo.save(newList);
    }

    @PutMapping("/lists/{id}")
    ListModel updateList(@RequestBody ListModel newList, @PathVariable Long id){
        return listRepo.findById(id).map(list -> {
            // Update relevant fields.
            list.setName(newList.getName());
            return listRepo.save(list);
        }).orElseGet(() -> {
            // Or create newList using id.
            newList.setId(id);
            return listRepo.save(newList);
        });
        
    }

    @DeleteMapping("/lists/{id}")
    void deleteCard(@PathVariable Long id) {
        listRepo.deleteById(id);
    }

}
