package com.backend.kanbanboard.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.backend.kanbanboard.controllers.BoardController;
import com.backend.kanbanboard.models.Board;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class BoardModelAssembler implements RepresentationModelAssembler<Board, EntityModel<Board>>{
    @Override
    public EntityModel<Board> toModel(Board board){
        return EntityModel.of(board, 
            linkTo(methodOn(BoardController.class).one(board.getId())).withSelfRel(),
            linkTo(methodOn(BoardController.class).all()).withRel("boards")
            );
    }
}
