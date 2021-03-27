package com.backend.kanbanboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.kanbanboard.exceptions.CardNotFoundException;
import com.backend.kanbanboard.exceptions.ListNotFoundException;
import com.backend.kanbanboard.models.Board;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.services.KanbanService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class KanbanServiceImplTests {
    
    private Card card;
    
    private ListModel list;

    private Board board;

    @Autowired
    private KanbanService kanbanService;

    @BeforeEach
    void init() {
        board = kanbanService.createBoard(new Board(1L, "board1"));
        list = kanbanService.createList(new ListModel(1L, "list1", board));
        card = kanbanService.createCard(new Card(1L, "card1", "", list));
    }

    @Test
    public void testDeleteListAndCardOnList() {
        // Make sure that when a ListModel is deleted,
        // then a Card on it is deleted as well.

        kanbanService.deleteList(list.getId());

        assertThrows(CardNotFoundException.class, () -> {
            kanbanService.getCard(card.getId());
        });
    }

    @Test
    public void testDeleteBoardAndListOnBoard() {
        // Make sure that when a Board is deleted,
        // then a ListModel on it is deleted as well.

        kanbanService.deleteBoard(board.getId());

        assertThrows(ListNotFoundException.class, () -> {
            kanbanService.getList(list.getId());
        });
    }

    @Test
    public void testUpdateCardDoesntOverwriteId(){
        // Make sure that entity id can't be overwritten by user during PUT update.
        Card newCard = new Card(100L, "newCard", "", list);

        Card updatedCard = kanbanService.updateCard(newCard, card.getId());

        assertEquals(card.getId(), updatedCard.getId());
    }

    @Test
    public void testUpdateListDoesntOverwriteId(){
        // Make sure that entity id can't be overwritten by user during PUT update.
        ListModel newList = new ListModel(100L, "newList", board);

        ListModel updatedList = kanbanService.updateList(newList, list.getId());

        assertEquals(list.getId(), updatedList.getId());
    }

    @Test
    public void testUpdateBoardDoesntOverwriteId(){
        // Make sure that entity id can't be overwritten by user during PUT update.
        Board newBoard = new Board(100L, "newBoard");

        Board updatedBoard = kanbanService.updateBoard(newBoard, board.getId());

        assertEquals(board.getId(), updatedBoard.getId());
    }
}
