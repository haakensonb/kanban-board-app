package com.backend.kanbanboard.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.backend.kanbanboard.controllers.ListController;
import com.backend.kanbanboard.models.ListModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ListModelAssembler implements RepresentationModelAssembler<ListModel, EntityModel<ListModel>>{
    @Override
    public EntityModel<ListModel> toModel(ListModel list){
        return EntityModel.of(list, 
            linkTo(methodOn(ListController.class).one(list.getId())).withSelfRel(),
            linkTo(methodOn(ListController.class).all()).withRel("lists")
            );
    }
}
