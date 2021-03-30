package com.backend.kanbanboard.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import com.backend.kanbanboard.assemblers.BoardModelAssembler;
import com.backend.kanbanboard.assemblers.ListModelAssembler;
import com.backend.kanbanboard.models.Board;
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
public class BoardController {
    private final KanbanService kanbanService;

    private final BoardModelAssembler assembler;

    private final ListModelAssembler listAssembler;

    public BoardController(KanbanService kanbanService, BoardModelAssembler assembler, ListModelAssembler listAssembler) {
        this.kanbanService = kanbanService;
        this.assembler = assembler;
        this.listAssembler = listAssembler;
    }

    @GetMapping("/boards")
    public CollectionModel<EntityModel<Board>> all() {
        List<EntityModel<Board>> boards = kanbanService.getAllBoards().stream().map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(boards, linkTo(methodOn(BoardController.class).all()).withSelfRel());
    }

    @GetMapping("/boards/{id}")
    public EntityModel<Board> one(@PathVariable Long id) {
        Board board = kanbanService.getBoard(id);
        return assembler.toModel(board);
    }

    @GetMapping("/boards/{id}/lists")
    public CollectionModel<EntityModel<ListModel>> oneWithLists(@PathVariable Long id) {
        List<EntityModel<ListModel>> lists = kanbanService.getBoardLists(id).stream().map(listAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lists, linkTo(methodOn(BoardController.class).oneWithLists(id)).withSelfRel());
    }

    @PostMapping("/boards")
    public ResponseEntity<?> newBoard(@RequestBody Board newBoard) {
        EntityModel<Board> entityModel = assembler.toModel(kanbanService.createBoard(newBoard));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/boards/{id}")
    public ResponseEntity<?> updateBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        EntityModel<Board> entityModel = assembler.toModel(kanbanService.updateBoard(newBoard, id));

        // "created" may not be the best response code.
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/boards/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id) {
        kanbanService.deleteBoard(id);

        return ResponseEntity.noContent().build();
    }
}
