package com.backend.kanbanboard.services;

import java.util.List;

import com.backend.kanbanboard.exceptions.CardNotFoundException;
import com.backend.kanbanboard.exceptions.ListNotFoundException;
import com.backend.kanbanboard.models.Card;
import com.backend.kanbanboard.repositories.CardRepository;
import com.backend.kanbanboard.repositories.ListRepository;

import org.springframework.stereotype.Service;

@Service
public class KanbanServiceImpl implements KanbanService {
    private final CardRepository cardRepo;

    private final ListRepository listRepo;

    public KanbanServiceImpl(CardRepository cardRepo, ListRepository listRepo){
        this.cardRepo = cardRepo;
        this.listRepo = listRepo;
    }

    // Card service methods
    public List<Card> getAllCards(){
        return cardRepo.findAll();
    }

    public Card getCard(Long id){
        return cardRepo.findById(id).orElseThrow(() -> new CardNotFoundException(id));
    }

    public Card createCard(Card newCard){
        Long listId = newCard.getList().getId();
        // Make sure that card references a valid list before saving.
        listRepo.findById(listId).orElseThrow(() -> new ListNotFoundException(listId));
        
        return cardRepo.save(newCard);
    }

    public Card updateCard(Card newCard, Long id){
        return cardRepo.findById(id).map(card -> {
            card.setTitle(newCard.getTitle());
            card.setDescription(newCard.getDescription());
            return cardRepo.save(card);
        }).orElseGet(() -> {
            newCard.setId(id);
            return cardRepo.save(newCard);
        });
    }

    public void deleteCard(Long id){
        cardRepo.deleteById(id);
    }

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
