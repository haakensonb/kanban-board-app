package com.backend.kanbanboard.exceptions;

public class BoardNotFoundException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BoardNotFoundException(Long id){
        super("Could not find board " + id);
    }
}
