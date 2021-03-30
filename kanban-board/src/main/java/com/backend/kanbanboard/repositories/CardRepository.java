package com.backend.kanbanboard.repositories;

import java.util.List;

import com.backend.kanbanboard.models.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

// Spring auto configures basic CRUD operations if this interface is declared.
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByListId(Long listId);

    @Transactional
    void deleteByListId(Long listId);
}
