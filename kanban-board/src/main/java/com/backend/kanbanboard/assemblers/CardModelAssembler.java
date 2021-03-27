package com.backend.kanbanboard.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.backend.kanbanboard.controllers.CardController;
import com.backend.kanbanboard.models.Card;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class CardModelAssembler implements RepresentationModelAssembler<Card, EntityModel<Card>>{
    @Override
    public EntityModel<Card> toModel(Card card){
        return EntityModel.of(card, 
            linkTo(methodOn(CardController.class).one(card.getId())).withSelfRel(),
            linkTo(methodOn(CardController.class).all()).withRel("cards")
            );
    }
}
