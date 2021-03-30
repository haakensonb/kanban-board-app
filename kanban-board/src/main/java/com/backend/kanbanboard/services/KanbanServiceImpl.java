package com.backend.kanbanboard.services;

import java.util.List;

import com.backend.kanbanboard.exceptions.BoardNotFoundException;
import com.backend.kanbanboard.exceptions.CardNotFoundException;
import com.backend.kanbanboard.exceptions.ListNotFoundException;
import com.backend.kanbanboard.models.Board;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.models.ListModel;
import com.backend.kanbanboard.repositories.BoardRepository;
import com.backend.kanbanboard.repositories.CardRepository;
import com.backend.kanbanboard.repositories.ListRepository;

import org.springframework.stereotype.Service;

@Service
public class KanbanServiceImpl implements KanbanService {
    private final CardRepository cardRepo;

    private final ListRepository listRepo;

    private final BoardRepository boardRepo;

    public KanbanServiceImpl(CardRepository cardRepo, ListRepository listRepo, BoardRepository boardRepo) {
        this.cardRepo = cardRepo;
        this.listRepo = listRepo;
        this.boardRepo = boardRepo;
    }

    // Card service methods
    public List<Card> getAllCards() {
        return cardRepo.findAll();
    }

    public Card getCard(Long id) {
        return cardRepo.findById(id).orElseThrow(() -> new CardNotFoundException(id));
    }

    public Card createCard(Card newCard) {
        Long listId = newCard.getList().getId();
        // Make sure that card references a valid list before saving.
        listRepo.findById(listId).orElseThrow(() -> new ListNotFoundException(listId));

        return cardRepo.save(newCard);
    }

    public Card updateCard(Card newCard, Long id) {
        return cardRepo.findById(id).map(card -> {
            card.setTitle(newCard.getTitle());
            card.setDescription(newCard.getDescription());
            return cardRepo.save(card);
        }).orElseGet(() -> {
            newCard.setId(id);
            return cardRepo.save(newCard);
        });
    }

    public void deleteCard(Long id) {
        cardRepo.deleteById(id);
    }

    // ListModel service methods
    public List<ListModel> getAllLists() {
        return listRepo.findAll();
    }

    public ListModel getList(Long id) {
        return listRepo.findById(id).orElseThrow(() -> new ListNotFoundException(id));
    }

    public List<Card> getListCards(Long id) {
        ListModel list = listRepo.findById(id).orElseThrow(() -> new ListNotFoundException(id));
        return cardRepo.findByListId(list.getId());
    }

    public ListModel createList(ListModel newList) {
        return listRepo.save(newList);
    }

    public ListModel updateList(ListModel newList, Long id) {
        return listRepo.findById(id).map(list -> {
            // Update relevant fields.
            list.setName(newList.getName());
            list.setBoard(newList.getBoard());
            return listRepo.save(list);
        }).orElseGet(() -> {
            // Or create newList using id.
            newList.setId(id);
            return listRepo.save(newList);
        });
    }

    public void deleteList(Long id) {
        listRepo.deleteById(id);
    }

    // Board service methods
    public List<Board> getAllBoards(){
        return boardRepo.findAll();
    }
    
    public Board getBoard(Long id){
        return boardRepo.findById(id).orElseThrow(() -> new BoardNotFoundException(id));
    }
    
    public List<ListModel> getBoardLists(Long id){
        Board board = boardRepo.findById(id).orElseThrow(() -> new BoardNotFoundException(id));
        return listRepo.findByBoardId(board.getId());
    }
    
    public Board createBoard(Board newBoard){
        return boardRepo.save(newBoard);
    }
    
    public Board updateBoard(Board newBoard, Long id){
        return boardRepo.findById(id).map(board -> {
            // Update board if it exists.
            board.setName(newBoard.getName());
            return boardRepo.save(board);
        }).orElseGet(() -> {
            // Otherwise create new board.
            newBoard.setId(id);
            return boardRepo.save(newBoard);
        });
    }
    
    public void deleteBoard(Long id){
        boardRepo.deleteById(id);
    }
    
}
