package com.backend.kanbanboard.repositories;

import com.backend.kanbanboard.models.ListModel;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring auto configures basic CRUD operations if this interface is declared.
public interface ListRepository extends JpaRepository<ListModel, Long> {

}
