package com.backend.kanbanboard.repositories;

import java.util.List;

import com.backend.kanbanboard.models.ListModel;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring auto configures basic CRUD operations if this interface is declared.
public interface ListRepository extends JpaRepository<ListModel, Long> {
    List<ListModel> findByBoardId(Long boardId);
}
