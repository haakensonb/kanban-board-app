package com.backend.kanbanboard.services;

import java.util.List;

import com.backend.kanbanboard.models.Card;

/**
 * Service provides an interface from Card, ListModel, and Board objects
 * since they are all closely related.
 */
public interface KanbanService {
    // Card service methods
    List<Card> getAllCards();
    Card getCard(Long id);
    Card createCard(Card newCard);
    Card updateCard(Card newCard, Long id);
    void deleteCard(Long id);

    // // ListModel service methods
    // List<ListModel> getAllLists();
    // ListModel getList(Long id);
    // List<Card> getListCards(Long id);
    // ListModel createList(ListModel newList);
    // ListModel updateList(ListModel newList, Long id);
    // void deleteList(Long id);

    // // Board service methods
    // List<Board> getAllBoards();
    // Board getBoard(Long id);
    // List<ListModel> getBoardLists(Long id);
    // Board createBoard(Board newBoard);
    // Board updateBoard(Board newBoard, Long id);
    // void deleteBoard(Long id);

}
