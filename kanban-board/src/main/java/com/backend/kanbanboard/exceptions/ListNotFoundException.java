package com.backend.kanbanboard.exceptions;

public class ListNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ListNotFoundException(Long id){
        super("Could not find list " + id);
    }
    
}
