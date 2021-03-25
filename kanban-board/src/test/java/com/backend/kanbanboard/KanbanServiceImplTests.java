package com.backend.kanbanboard;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.backend.kanbanboard.exceptions.CardNotFoundException;
import com.backend.kanbanboard.exceptions.ListNotFoundException;
import com.backend.kanbanboard.models.Board;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.services.KanbanService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class KanbanServiceImplTests {
    @InjectMocks
    private Card card;

    @InjectMocks
    private ListModel list;

    @InjectMocks
    private Board board;

    @Autowired
    private KanbanService kanbanService;

    @BeforeEach
    void init() {
        board.setId(1L);
        list.setId(1L);
        list.setBoard(board);
        card.setId(1L);
        card.setList(list);
    }

    @Test
    public void testDeleteListAndCardOnList() {
        // Make sure that when a ListModel is deleted,
        // then a Card on it is deleted as well.
        kanbanService.createBoard(board);
        kanbanService.createList(list);
        kanbanService.createCard(card);

        kanbanService.deleteList(1L);

        assertThrows(CardNotFoundException.class, () -> {
            kanbanService.getCard(1L);
        });
    }

    @Test
    public void testDeleteBoardAndListOnBoard() {
        // Make sure that when a Board is deleted,
        // then a ListModel on it is deleted as well.
        kanbanService.createBoard(board);
        kanbanService.createList(list);
        kanbanService.createCard(card);

        kanbanService.deleteBoard(1L);

        assertThrows(ListNotFoundException.class, () -> {
            kanbanService.getList(1L);
        });
    }
}
