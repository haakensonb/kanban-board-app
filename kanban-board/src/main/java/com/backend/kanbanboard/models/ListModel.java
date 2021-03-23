package com.backend.kanbanboard.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "list_model")
public class ListModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    private String name;

    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "list")
    // @JsonManagedReference
    // private List<Card> cards = new ArrayList<Card>();

    public ListModel() {
    }

    public ListModel(String name) {
        this.name = name;
    }

    public ListModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // public ListModel(String name, List<Card> cards) {
    //     this.name = name;
    //     this.cards = cards;
    // }

    // @PrePersist
    // public void populateCards(){
    //     for(Card card : this.cards){
    //         card.setList(this);
    //     }
    // }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public List<Card> getCards() {
    //     return this.cards;
    // }

    // public void setCards(List<Card> cards) {
    //     this.cards = cards;
    // }

    // public void addCard(Card newCard){
    //     this.cards.add(newCard);
    // }

    // @Override
    // public boolean equals(Object o) {
    //     if (o == this)
    //         return true;
    //     if (!(o instanceof ListModel)) {
    //         return false;
    //     }
    //     ListModel listModel = (ListModel) o;
    //     return Objects.equals(getId(), listModel.getId()) && Objects.equals(name, listModel.name)
    //             && Objects.equals(cards, listModel.cards);
    // }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(getId(), name, cards);
    // }

    // @Override
    // public String toString() {
    //     return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", cards='" + getCards() + "'" + "}";
    // }

}
