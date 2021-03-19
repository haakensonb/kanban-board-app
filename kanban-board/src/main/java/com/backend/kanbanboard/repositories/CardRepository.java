package com.backend.kanbanboard.repositories;

import com.backend.kanbanboard.models.Card;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring auto configures basic CRUD operations if this interface is declared.
public interface CardRepository extends JpaRepository<Card, Long> {

}
