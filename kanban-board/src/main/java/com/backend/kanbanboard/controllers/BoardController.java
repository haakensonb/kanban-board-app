package com.backend.kanbanboard.controllers;

import java.util.List;

import com.backend.kanbanboard.models.Board;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.services.KanbanService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    private final KanbanService kanbanService;

    BoardController(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    @GetMapping("/boards")
    List<Board> all() {
        return kanbanService.getAllBoards();
    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return kanbanService.getBoard(id);
    }

    @GetMapping("/boards/{id}/lists")
    List<ListModel> oneWithLists(@PathVariable Long id){
        return kanbanService.getBoardLists(id);
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard){
        return kanbanService.createBoard(newBoard);
    }

    @PutMapping("/boards/{id}")
    Board updateBoard(@RequestBody Board newBoard, @PathVariable Long id){
        return kanbanService.updateBoard(newBoard, id);
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id){
        kanbanService.deleteBoard(id);
    }
}
